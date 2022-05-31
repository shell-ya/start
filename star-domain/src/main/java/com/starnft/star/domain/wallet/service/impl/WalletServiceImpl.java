package com.starnft.star.domain.wallet.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.starnft.star.common.Result;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.WalletAddrGenerator;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.wallet.model.req.*;
import com.starnft.star.domain.wallet.model.res.CalculateResult;
import com.starnft.star.domain.wallet.model.res.CardBindResult;
import com.starnft.star.domain.wallet.model.res.WalletResult;
import com.starnft.star.domain.wallet.model.res.WithdrawResult;
import com.starnft.star.domain.wallet.model.vo.*;
import com.starnft.star.domain.wallet.repository.IWalletRepository;
import com.starnft.star.domain.wallet.service.WalletConfig;
import com.starnft.star.domain.wallet.service.WalletService;
import com.starnft.star.domain.wallet.service.stateflow.IStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.web3j.crypto.CipherException;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    @Resource
    private IWalletRepository walletRepository;

    @Resource
    private WalletAddrGenerator walletAddrGenerator;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedisLockUtils redisLockUtils;

    @Resource
    private Map<StarConstants.Ids, IIdGenerator> idGeneratorMap;

    @Resource
    private IStateHandler stateHandler;

    @Override
    @Transactional
    public WalletResult queryWalletInfo(WalletInfoReq walletInfoReq) {
        WalletVO walletVO = walletRepository.queryWallet(walletInfoReq);
        if (walletVO == null) {
            //生成钱包地址
            try {
                walletInfoReq.setWalletId(walletAddrGenerator.generate());
            } catch (CipherException | InvalidKeySpecException | NoSuchAlgorithmException e) {
                throw new RuntimeException("生成钱包地址失败！", e);
            }
            //创建钱包
            walletVO = walletRepository.createWallet(walletInfoReq);
        }
        WalletResult walletResult = new WalletResult();
        walletResult.setWalletId(walletVO.getWalletId());
        walletResult.setBalance(walletVO.getBalance());
        walletResult.setFrozen(walletVO.isFrozen());
        walletResult.setFrozen_fee(walletVO.getFrozen_fee());
        walletResult.setWallet_income(walletVO.getWallet_income());
        walletResult.setWallet_outcome(walletVO.getWallet_outcome());
        return walletResult;
    }

    @Override
    public boolean rechargeRecordGenerate(WalletRecordReq walletRecordReq) {

        if (Strings.isNullOrEmpty(walletRecordReq.getRecordSn())) {
            throw new StarException(StarError.VALUE_COULD_NOT_BE_NULL, "交易流水号不能为空");
        }

        WalletRecordVO walletRecordVO = walletRepository.queryWalletRecordBySerialNo(walletRecordReq.getRecordSn(), null);

        boolean isSuccess = false;
        if (walletRecordVO == null) {
            isSuccess = walletRepository.createWalletRecord(walletRecordReq);
        }

        return isSuccess;
    }

    @Override
    public CalculateResult withdrawMoneyCalculate(CalculateReq calculate) {

        WalletConfigVO config = WalletConfig.getConfig(StarConstants.PayChannel.valueOf(calculate.getChannel()));
        BigDecimal calculated = calculate.getMoney().subtract(calculate.getMoney().multiply(config.getChargeRate()));
        NumberFormat percent = NumberFormat.getPercentInstance();
        NumberFormat number = NumberFormat.getNumberInstance();
        number.setMaximumFractionDigits(3);
        percent.setMaximumFractionDigits(4);
        return new CalculateResult(number.format(calculated), percent.format(config.getChargeRate()));
    }

    private String verifyAndGetKey(WithDrawReq withDrawReq) {
        //提现次数确认
        WalletConfigVO config = WalletConfig.getConfig(StarConstants.PayChannel.valueOf(withDrawReq.getChannel()));
        String withdrawTimesKey = String.format(RedisKey.REDIS_WITHDRAW_TIMES.getKey(),
                new StringBuffer(String.valueOf(withDrawReq.getUid())).append(withDrawReq.getWalletId()));
        //是否超过当日提现次数
        if (redisUtil.hasKey(withdrawTimesKey) &&
                config.getWithdrawTimes() <= Integer.parseInt((String) redisUtil.get(withdrawTimesKey))) {
            throw new StarException(StarError.OVER_WITHDRAW_TIMES);
        }
        //是否超过限额
        if (withDrawReq.getMoney().compareTo(config.getWithdrawLimit()) > 0) {
            throw new StarException(StarError.OVER_WITHDRAW_MONEY);
        }
        //钱包交易状态中锁
        String isTransactionKey = String.format(RedisKey.REDIS_TRANSACTION_ING.getKey(),
                new StringBuffer(String.valueOf(withDrawReq.getUid())).append(withDrawReq.getWalletId()));
        //判断当前是否有其他交易正在进行
        if (redisUtil.hasKey(isTransactionKey)) {
            throw new StarException(StarError.IS_TRANSACTION);
        }
        return isTransactionKey;
    }

    @Override
    @Transactional
    public WithdrawResult withdraw(WithDrawReq withDrawReq) {
        //校验提现规则
        String isTransactionKey = verifyAndGetKey(withDrawReq);

        long withdrawTradeNo = idGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId();

        try {
            //锁定当前钱包交易
            if (redisLockUtils.lock(isTransactionKey, RedisKey.REDIS_TRANSACTION_ING.getTime())) {
                //查询钱包余额是否足够并将提现金额先扣除
                WalletResult walletResult = queryWalletInfo(new WalletInfoReq(withDrawReq.getUid()));
                //余额是否足够提现
                if (walletResult.getBalance().compareTo(withDrawReq.getMoney()) <= 0) {
                    throw new StarException(StarError.BALANCE_NOT_ENOUGH);
                }
                //修改钱包余额
                WalletVO walletVO = createWithdrawWalletVO(walletResult, withDrawReq);
                boolean aSuccess = walletRepository.modifyWalletBalance(walletVO);
                //记录提现记录 提现中
                WithdrawRecordVO withdrawRecordVO = createWithdrawRecordVO(withDrawReq, withdrawTradeNo);
                boolean bSuccess = walletRepository.createWithdrawRecord(withdrawRecordVO);
                //记录交易记录
                WalletRecordReq walletRecordReq = createWalletRecordReq(withDrawReq, withdrawTradeNo);
                boolean cSuccess = walletRepository.createWalletRecord(walletRecordReq);
                //缓存提现次数到Redis
                if (aSuccess && bSuccess && cSuccess) {
                    String withdrawTimesKey = String.format(RedisKey.REDIS_WITHDRAW_TIMES.getKey(),
                            new StringBuffer(String.valueOf(withDrawReq.getUid())).append(withDrawReq.getWalletId()));
                    redisUtil.incr(withdrawTimesKey, 1);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException("提现操作发生异常", e);
        } finally {
            redisLockUtils.unlock(isTransactionKey);
        }
        return new WithdrawResult(withdrawTradeNo, 0);
    }

    private WalletRecordReq createWalletRecordReq(WithDrawReq withDrawReq, long withdrawTradeNo) {
        return WalletRecordReq.builder().from_uid(withDrawReq.getUid()).to_uid(0L)
                .recordSn(String.valueOf(withdrawTradeNo)).checkStatus(0)
                .payChannel(withDrawReq.getChannel()).payStatus(StarConstants.Pay_Status.PAY_ING.name())
                .tsMoney(withDrawReq.getMoney()).tsType(StarConstants.Transaction_Type.Withdraw.getCode().toString())
                .build();
    }

    private WithdrawRecordVO createWithdrawRecordVO(WithDrawReq withDrawReq, long withdrawTradeNo) {
        return WithdrawRecordVO.builder().withdrawTradeNo(String.valueOf(withdrawTradeNo))
                .walletId(withDrawReq.getWalletId()).bankNo(withDrawReq.getBankNo())
                .cardName(withDrawReq.getCardName()).channel(withDrawReq.getChannel())
                .money(withDrawReq.getMoney()).uid(withDrawReq.getUid()).build();
    }

    private WalletVO createWithdrawWalletVO(WalletResult walletResult, WithDrawReq withDrawReq) {
        //提现后余额
        BigDecimal balance = walletResult.getBalance().subtract(withDrawReq.getMoney().abs());
        // 提现成功后 清除冻结金额 提现失败 或取消提现 余额加冻结资金 清除冻结资金还原支出金额
        return WalletVO.builder().
                balance(balance)
                .frozen_fee(withDrawReq.getMoney().abs())
                .wallet_outcome(walletResult.getWallet_outcome().add(withDrawReq.getMoney().abs())).
                build();
    }


    @Override
    public ResponsePageResult<WalletRecordVO> queryTransactionRecord(TransactionRecordQueryReq queryReq) {
        return walletRepository.queryTransactionRecordByCondition(queryReq);
    }

    @Override
    @Transactional
    public boolean rechargeProcess(@Validated RechargeVO rechargeVO) {
        WalletVO walletVO = walletRepository.queryWallet(new WalletInfoReq(Long.valueOf(rechargeVO.getUid())));
        BigDecimal curr = walletVO.getBalance().add(rechargeVO.getTotalAmount().abs());
        BigDecimal income = walletVO.getWallet_income().add(curr);

        //修改交易记录状态 会写第三方流水号
        WalletRecordVO walletRecordVO = walletRepository.queryWalletRecordBySerialNo(rechargeVO.getOrderSn(), StarConstants.Pay_Status.PAY_ING.name());
        if (null == walletRecordVO) {
            log.error("钱包交易记录状态变化出错,交易记录单号：[{}] , 第三方交易流水号： [{}] ", rechargeVO.getOrderSn(), rechargeVO.getTransSn());
            throw new RuntimeException("钱包交易记录状态变化出错");
        }

        Result result = stateHandler.paySuccess(rechargeVO.getOrderSn(), rechargeVO.getTransSn(), null);
        //记录余额变动记录
        boolean logWrite = walletRepository.createWalletLog(RechargeReq.builder().walletId(walletVO.getWalletId())
                .userId(walletVO.getUid()).money(rechargeVO.getTotalAmount()).currentMoney(curr).payChannel(rechargeVO.getPayChannel())
                .payNo(rechargeVO.getOrderSn()).build());

        //修改余额
        boolean balanceModify = walletRepository.modifyWalletBalance(WalletVO.builder().uid(Long.valueOf(rechargeVO.getUid()))
                .balance(curr).wallet_outcome(income).build());

        // redis写入交易成功信息 前端轮训状态直接查redis
        if (logWrite && balanceModify && result.getCode().equals(ResultCode.SUCCESS.getCode())) {
            redisUtil.set(String.format(RedisKey.REDIS_TRANSACTION_SUCCESS.getKey()
                    , rechargeVO.getOrderSn()), 1, RedisKey.REDIS_TRANSACTION_SUCCESS.getTime());
        }

        return logWrite && balanceModify && result.getCode().equals(ResultCode.SUCCESS.getCode());
    }

    @Override
    public boolean cardBind(CardBindReq cardBindReq) {
        return walletRepository.cardBinding(BankRelationVO.builder().uid(cardBindReq.getUid()).cardNo(cardBindReq.getCardNo().toString())
                .cardName(cardBindReq.getCardName()).Nickname(cardBindReq.getNickname()).isDefault(cardBindReq.getIsDefault()).phone(cardBindReq.getPhone())
                .bankShortName(cardBindReq.getBankShortName()).build());
    }

    @Override
    public List<CardBindResult> obtainCards(Long uid) {
        List<BankRelationVO> relations = walletRepository.queryCardBindings(uid);
        ArrayList<@Nullable CardBindResult> results = Lists.newArrayList();
        for (BankRelationVO relation : relations) {
            results.add(new CardBindResult(relation.getCardNo(), relation.getCardName(), relation.getBankShortName(), relation.getIsDefault()));
        }
        return results;
    }

    @Override
    public boolean deleteCards(List<BankRelationVO> bankRelations) {
        return walletRepository.deleteCard(bankRelations);
    }

    @Override
    public boolean setDefaultCard(BankRelationVO relationVO) {
        return walletRepository.setDefaultCard(relationVO);
    }


}
