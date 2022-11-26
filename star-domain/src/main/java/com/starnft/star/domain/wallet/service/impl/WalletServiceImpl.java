package com.starnft.star.domain.wallet.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.starnft.star.common.Result;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.StarUtils;
import com.starnft.star.common.utils.WalletAddrGenerator;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.wallet.model.req.*;
import com.starnft.star.domain.wallet.model.res.*;
import com.starnft.star.domain.wallet.model.vo.*;
import com.starnft.star.domain.wallet.repository.IWalletRepository;
import com.starnft.star.domain.wallet.service.WalletConfig;
import com.starnft.star.domain.wallet.service.WalletService;
import com.starnft.star.domain.wallet.service.stateflow.IStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.web3j.crypto.CipherException;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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

    @Resource
    private TransactionTemplate template;

    private ThreadLocal<Boolean> isVerified = new ThreadLocal<>();


    /**
     * @param channel
     * @author Ryan Z / haoran
     * @description 参数验证
     * @date 2022/5/12
     */
    @Override
    public void verifyParam(String channel) {
        boolean exist = true;
        for (StarConstants.PayChannel channelName : StarConstants.PayChannel.values()) {
            if (channelName.name().equals(channel)) {
                exist = false;
                break;
            }
        }
        if (exist) {
            throw new StarException(StarError.PARAETER_UNSUPPORTED, "渠道代码不存在！");
        }
    }

    @Override
    public void balanceVerify(Long uid, BigDecimal money) {

        if (0L == uid){
            isVerified.set(Boolean.TRUE);
            return;
        }

        WalletResult walletResult = queryWalletInfo(new WalletInfoReq(uid));
        if (walletResult.getBalance().compareTo(money.abs()) < 0) {
            throw new StarException(StarError.BALANCE_NOT_ENOUGH);
        }
        isVerified.set(Boolean.TRUE);
    }

    @Override
    public void threadClear() {
        this.isVerified.remove();
    }

    @Override
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
    public WalletPayResult doWalletPay(WalletPayRequest walletPayRequest) {
        String isTransactionKey = String.format(RedisKey.REDIS_TRANSACTION_ING.getKey(), walletPayRequest.getUserId());
        Boolean lock = redisLockUtils.lock(isTransactionKey, RedisKey.REDIS_TRANSACTION_ING.getTimeUnit().toSeconds(RedisKey.REDIS_TRANSACTION_ING.getTime()));
        Assert.isTrue(lock, () -> new RuntimeException("用户 [" + walletPayRequest.getUserId() + "] orderSn: [ " + walletPayRequest.getOrderSn() + "] 钱包正在支付！"));
        try {
            Boolean isSuccess = template.execute(status -> {
                //创建或修改交易记录表
                boolean doRecord = false;
                if (walletPayRequest.getDoModifyRecord() == null) {
                    doRecord = rechargeRecordGenerate(buildRecordReq(walletPayRequest));
                } else {
                    Result result = walletPayRequest.getDoModifyRecord().get();
                    doRecord = ResultCode.SUCCESS.getCode().equals(result.getCode());
                }
                //记录钱包log 修改余额
                boolean doTransaction = doTransactionNoVerify(createTransReq(walletPayRequest));
                boolean marketTransaction = true;
                if (walletPayRequest.getOrderSn().startsWith(StarConstants.OrderPrefix.TransactionSn.getPrefix())){
                    marketTransaction =  doTransactionNoVerify(createMarketTransReq(walletPayRequest));
                }
//                boolean marketTransaction = !walletPayRequest.getOrderSn().startsWith(StarConstants.OrderPrefix.TransactionSn.getPrefix()) || doTransaction(createMarketTransReq(walletPayRequest));

                return doRecord && doTransaction && marketTransaction;
            });
            if (isSuccess) {
                return new WalletPayResult(walletPayRequest.getOrderSn(), walletPayRequest.getOutTradeNo(), ResultCode.SUCCESS.getCode(), new Date());
            }
        } finally {
            redisLockUtils.unlock(isTransactionKey);
        }
        throw new StarException(StarError.BALANCE_PAY_ERROR);
    }

    private TransReq createMarketTransReq(WalletPayRequest walletPayRequest) {
        walletPayRequest.setPayAmount(walletPayRequest.getPayAmount().signum() == -1 ? walletPayRequest.getPayAmount().negate() : walletPayRequest.getPayAmount());
        walletPayRequest.setPayAmount(walletPayRequest.getPayAmount().subtract(walletPayRequest.getFee()));
        walletPayRequest.setTotalPayAmount(walletPayRequest.getTotalPayAmount().subtract(walletPayRequest.getFee()));
        TransReq transReq = new TransReq();
        transReq.setOrderSn(walletPayRequest.getOrderSn());
        transReq.setUid(walletPayRequest.getToUid());
        transReq.setOutTradeNo(walletPayRequest.getOutTradeNo());
        transReq.setPayAmount(walletPayRequest.getPayAmount());
        transReq.setPayChannel(walletPayRequest.getChannel());
        transReq.setTotalAmount(walletPayRequest.getTotalPayAmount());
        transReq.setTsType(StarConstants.Transaction_Type.Sell.getCode());
        return transReq;
    }

    private TransReq createTransReq(WalletPayRequest walletPayRequest) {
        TransReq transReq = new TransReq();
        transReq.setOrderSn(walletPayRequest.getOrderSn());
        transReq.setUid(walletPayRequest.getUserId());
        transReq.setOutTradeNo(walletPayRequest.getOutTradeNo());
        transReq.setPayAmount(walletPayRequest.getPayAmount());
        transReq.setPayChannel(walletPayRequest.getChannel());
        transReq.setTotalAmount(walletPayRequest.getTotalPayAmount());
        transReq.setTsType(walletPayRequest.getType());
        return transReq;
    }

    private WalletRecordReq buildRecordReq(WalletPayRequest walletPayRequest) {
        return WalletRecordReq.builder().recordSn(walletPayRequest.getOrderSn()).payTime(new Date())
                .payStatus(walletPayRequest.getStatus()).payChannel(walletPayRequest.getChannel())
                .tsMoney(walletPayRequest.getTotalPayAmount()).to_uid(walletPayRequest.getToUid())
                .from_uid(walletPayRequest.getFromUid()).tsCost(walletPayRequest.getPayAmount())
                .tsType(walletPayRequest.getType()).tsFee(walletPayRequest.getFee()).build();
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
    public WalletRecordVO queryWalletRecordByOrderNo(String orderNo) {
        return walletRepository.queryWalletRecordBySerialNo(orderNo, null);
    }

    @Override
    public CalculateResult withdrawMoneyCalculate(CalculateReq calculate) {

        verifyParam(calculate.getChannel());
        WalletConfigVO config = WalletConfig.getConfig(StarConstants.PayChannel.valueOf(calculate.getChannel()));
        BigDecimal calculated = calculate.getMoney().subtract(calculate.getMoney().multiply(config.getChargeRate()));
        NumberFormat percent = NumberFormat.getPercentInstance();
        NumberFormat number = NumberFormat.getNumberInstance();
        number.setMaximumFractionDigits(3);
        percent.setMaximumFractionDigits(4);
        return new CalculateResult(number.format(calculated.setScale(2, BigDecimal.ROUND_HALF_DOWN)), percent.format(config.getChargeRate()));
    }

    private String verifyAndGetKey(WithDrawReq withDrawReq, WalletConfigVO config) {
        //提现次数确认
        if (null == config) {
            throw new RuntimeException("该渠道钱包配置为空");
        }
        String withdrawTimesKey = String.format(RedisKey.REDIS_WITHDRAW_TIMES.getKey(),
                new StringBuffer(String.valueOf(withDrawReq.getUid())).append(withDrawReq.getWalletId()));
        //是否超过当日提现次数
        if (redisUtil.hasKey(withdrawTimesKey) &&
                config.getWithdrawTimes() <= (Integer) redisUtil.get(withdrawTimesKey)) {
            throw new StarException(StarError.OVER_WITHDRAW_TIMES);
        }
        //是否超过限额
        if (new BigDecimal(withDrawReq.getMoney()).compareTo(config.getWithdrawLimit()) > 0) {
            throw new StarException(StarError.OVER_WITHDRAW_MONEY);
        }
        //钱包交易状态中锁
        String isTransactionKey = String.format(RedisKey.REDIS_TRANSACTION_ING.getKey(), withDrawReq.getUid());
        //判断是否有正在进行的提现订单
        List<WithdrawRecordVO> records = walletRepository.usersWithdrawRecords(withDrawReq.getUid());
        for (WithdrawRecordVO record : records) {
            if (record.getStatus() == 0) {
                throw new StarException(StarError.IS_WITHDRAWING);
            }
        }
        //判断当前是否有其他交易正在进行
        if (redisUtil.hasKey(RedisLockUtils.REDIS_LOCK_PREFIX + isTransactionKey)) {
            throw new StarException(StarError.IS_TRANSACTION);
        }
        return isTransactionKey;
    }

    @Override
    public WithdrawResult withdraw(WithDrawReq withDrawReq) {
        //校验提现规则
        WalletConfigVO config = WalletConfig.getConfig(StarConstants.PayChannel.valueOf(withDrawReq.getChannel()));
        String isTransactionKey = verifyAndGetKey(withDrawReq, config);
        //生成流水号
        String withdrawTradeNo = StarConstants.OrderPrefix.WithdrawSn.getPrefix().concat(
                String.valueOf(idGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId()));
        BigDecimal curr = null;
        //锁定当前钱包交易
        Boolean lock = redisLockUtils.lock(isTransactionKey, RedisKey.REDIS_TRANSACTION_ING.getTimeUnit().toSeconds(RedisKey.REDIS_TRANSACTION_ING.getTime()));
        Assert.isTrue(lock, () -> new RuntimeException("用户 [" + withDrawReq.getUid() + "] 正在申请提现！"));
        try {
            //查询钱包余额是否足够并将提现金额先扣除
            WalletResult walletResult = queryWalletInfo(new WalletInfoReq(withDrawReq.getUid()));
            if (!isVerified.get()) {
                //余额是否足够提现
                if (walletResult.getBalance().compareTo(new BigDecimal(withDrawReq.getMoney())) <= 0) {
                    throw new StarException(StarError.BALANCE_NOT_ENOUGH);
                }
            }
            //提现申请后用户钱包余额
            curr = new BigDecimal(String.valueOf(walletResult.getBalance().subtract(new BigDecimal(withDrawReq.getMoney()).abs())));
            Boolean isSuccess = template.execute(status -> {
                //修改钱包余额
                WalletVO walletVO = createWithdrawWalletVO(walletResult, withDrawReq);
                boolean aSuccess = walletRepository.modifyWalletBalance(walletVO);
                //记录提现记录 提现中
                WithdrawRecordVO withdrawRecordVO = createWithdrawRecordVO(withDrawReq, withdrawTradeNo);
                boolean bSuccess = walletRepository.createWithdrawRecord(withdrawRecordVO);
                //记录交易记录
                WalletRecordReq walletRecordReq = createWalletRecordReq(withDrawReq, config, withdrawTradeNo);
                boolean cSuccess = walletRepository.createWalletRecord(walletRecordReq);
                if (!(aSuccess && bSuccess && cSuccess)) {
                    throw new RuntimeException(StarError.DB_RECORD_UNEXPECTED_ERROR.getErrorMessage());
                }
                return true;
            });

            //缓存提现次数到Redis
            if (Boolean.TRUE.equals(isSuccess)) {
                String withdrawTimesKey = String.format(RedisKey.REDIS_WITHDRAW_TIMES.getKey(),
                        new StringBuffer(String.valueOf(withDrawReq.getUid())).append(withDrawReq.getWalletId()));
                redisUtil.incr(withdrawTimesKey, 1);
                Calendar instance = Calendar.getInstance();
                instance.add(Calendar.DATE, 1);
                instance.set(Calendar.HOUR_OF_DAY, 0);
                instance.set(Calendar.SECOND, 0);
                instance.set(Calendar.MINUTE, 0);
                instance.set(Calendar.MILLISECOND, 0);
                redisUtil.expire(withdrawTimesKey, (instance.getTimeInMillis() - System.currentTimeMillis()) / 1000, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            throw new RuntimeException("提现操作发生异常", e);
        } finally {
            redisLockUtils.unlock(isTransactionKey);
        }
        assert curr != null;
        return new WithdrawResult(withdrawTradeNo, StarUtils.formatMoney(curr), 0);
    }

    @Override
    public WithdrawResult withdrawCancel(WithdrawCancelReq cancelReq) {
        //钱包交易状态中锁
        String isTransactionKey = String.format(RedisKey.REDIS_TRANSACTION_ING.getKey(), cancelReq.getUid());
        //判断当前是否有其他交易正在进行
        if (redisUtil.hasKey(isTransactionKey)) {
            throw new StarException(StarError.IS_TRANSACTION);
        }
        AtomicReference<BigDecimal> curr = new AtomicReference<>();
        Boolean lock = redisLockUtils.lock(isTransactionKey, RedisKey.REDIS_TRANSACTION_ING.getTimeUnit().toSeconds(RedisKey.REDIS_TRANSACTION_ING.getTime()));
        Assert.isTrue(lock, () -> new RuntimeException("用户 [" + cancelReq.getUid() + "] withdrawSn: [ " + cancelReq.getWithdrawSn() + "] 正在取消提现！"));
        template.execute(status -> {
            //设置提现记录状态 -1
            boolean aSuccess = walletRepository.updateWithdrawApply(cancelReq.getWithdrawSn(), -1);
            //修改交易记录状态
            boolean bSuccess = walletRepository.updateWalletRecordStatus(cancelReq.getWithdrawSn(), StarConstants.Pay_Status.PAY_CLOSE.name());
            //回滚钱包金额
            WalletVO walletVO = createWithdrawCancelWalletVO(cancelReq);
            curr.set(walletVO.getBalance());
            boolean cSuccess = walletRepository.modifyWalletBalance(walletVO);
            if (!(aSuccess && bSuccess && cSuccess)) {
                throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR);
            }
            return true;
        });
        return new WithdrawResult(cancelReq.getWithdrawSn(), StarUtils.formatMoney(curr.get()), -1);
    }

    private WalletVO createWithdrawCancelWalletVO(WithdrawCancelReq cancelReq) {
        WalletVO walletVO = walletRepository.queryWallet(new WalletInfoReq(cancelReq.getWalletId(), cancelReq.getUid()));
        walletVO.setUid(cancelReq.getUid());
        walletVO.setWalletId(cancelReq.getWalletId());
        walletVO.setBalance(walletVO.getBalance().add(walletVO.getFrozen_fee().abs()));
        walletVO.setWallet_outcome(walletVO.getWallet_outcome().subtract(walletVO.getFrozen_fee().abs()));
        walletVO.setFrozen_fee(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_DOWN));
        return walletVO;
    }

    private WalletRecordReq createWalletRecordReq(WithDrawReq withDrawReq, WalletConfigVO config, String withdrawTradeNo) {
        BigDecimal money = new BigDecimal(withDrawReq.getMoney());
        BigDecimal cost = money.subtract(money.multiply(config.getChargeRate()));
        return WalletRecordReq.builder().from_uid(withDrawReq.getUid()).to_uid(0L)
                .recordSn(withdrawTradeNo)
                .payChannel(withDrawReq.getChannel()).payStatus(StarConstants.Pay_Status.PAY_ING.name())
                .payTime(new Date())
                .tsMoney(money)
                .tsCost(money.multiply(new BigDecimal(1).subtract(config.getChargeRate())))
                .tsFee(money.multiply(config.getChargeRate()))
                .tsType(StarConstants.Transaction_Type.Withdraw.getCode())
                .build();
    }

    private WithdrawRecordVO createWithdrawRecordVO(WithDrawReq withDrawReq, String withdrawTradeNo) {
        return WithdrawRecordVO.builder().withdrawTradeNo(withdrawTradeNo)
                .walletId(withDrawReq.getWalletId()).bankNo(Long.valueOf(withDrawReq.getBankNo()))
                .cardName(withDrawReq.getCardName()).channel(withDrawReq.getChannel())
                .money(new BigDecimal(withDrawReq.getMoney())).uid(withDrawReq.getUid()).build();
    }

    private WalletVO createWithdrawWalletVO(WalletResult walletResult, WithDrawReq withDrawReq) {
        //提现后余额
        BigDecimal balance = walletResult.getBalance().subtract(new BigDecimal(withDrawReq.getMoney()).abs());
        // 提现成功后 清除冻结金额 提现失败 或取消提现 余额加冻结资金 清除冻结资金还原支出金额
        return WalletVO.builder()
                .uid(withDrawReq.getUid())
                .balance(balance)
                .frozen_fee(walletResult.getFrozen_fee().add(new BigDecimal(withDrawReq.getMoney()).abs()))
                .wallet_outcome(walletResult.getWallet_outcome().add(new BigDecimal(withDrawReq.getMoney()).abs())).
                build();
    }


    @Override
    public ResponsePageResult<WalletRecordVO> queryTransactionRecord(TransactionRecordQueryReq queryReq) {

        ResponsePageResult<WalletRecordVO> walletRecordVOResponsePageResult = walletRepository.queryTransactionRecordByCondition(queryReq);

        for (WalletRecordVO walletRecordVO : walletRecordVOResponsePageResult.getList()) {
            if (walletRecordVO.getTsType() == 3) {
                if (walletRecordVO.getToUid().equals(queryReq.getUserId())) {
                    walletRecordVO.setTsType(4);
                }
            }
        }

        List<WalletRecordVO> collect = walletRecordVOResponsePageResult.getList().stream().peek(vo -> {
            if (vo.getTsType() == 3) {
                if (vo.getToUid().equals(queryReq.getUserId())) {
                    vo.setTsType(4);
                }
            }
        }).filter(record -> {
            if (Objects.nonNull(queryReq.getTransactionType())) {
                return queryReq.getTransactionType().contains(record.getTsType());
            }
            return true;
        }).collect(Collectors.toList());

        walletRecordVOResponsePageResult.setList(collect);
        return walletRecordVOResponsePageResult;
    }

    // TODO need to optimize with method doTransaction(TransReq transReq)
    @Override
    public boolean rechargeProcess(@Validated RechargeVO rechargeVO) {

        verifyParam(rechargeVO.getPayChannel());

        WalletVO walletVO = walletRepository.queryWallet(new WalletInfoReq(Long.valueOf(rechargeVO.getUid())));
        //充值后当前金额
        BigDecimal curr = walletVO.getBalance().add(rechargeVO.getTotalAmount().abs());
        //增加总收入金额
        BigDecimal income = walletVO.getWallet_income().add(rechargeVO.getTotalAmount().abs());

        Boolean isSuccess = template.execute(status -> {
            //修改交易记录状态 回写第三方流水号
            WalletRecordVO walletRecordVO = walletRepository.queryWalletRecordBySerialNo(rechargeVO.getOrderSn(), StarConstants.Pay_Status.PAY_ING.name());
            if (null == walletRecordVO) {
                log.error("钱包交易记录状态变化出错,交易记录单号：[{}] , 第三方交易流水号： [{}] ", rechargeVO.getOrderSn(), rechargeVO.getTransSn());
                throw new RuntimeException("钱包交易记录状态变化出错,可能出现消息重复消费");
            }

            Result result = stateHandler.paySuccess(rechargeVO.getOrderSn(),
                    rechargeVO.getTransSn(), curr, StarConstants.Pay_Status.valueOf(walletRecordVO.getPayStatus()));

            //记录余额变动记录
            boolean logWrite = walletRepository.createWalletLog(WalletLogReq.builder().walletId(walletVO.getWalletId())
                    .userId(walletVO.getUid()).offset(rechargeVO.getTotalAmount()).currentMoney(curr).payChannel(rechargeVO.getPayChannel())
                    .orderNo(rechargeVO.getOrderSn()).build());

            //修改余额
            boolean balanceModify = walletRepository.modifyWalletBalance(WalletVO.builder().uid(Long.valueOf(rechargeVO.getUid()))
                    .balance(curr).wallet_income(income).build());
            return logWrite && balanceModify && result.getCode().equals(ResultCode.SUCCESS.getCode());
        });

        // redis写入交易成功信息 前端轮训状态直接查redis
        if (Boolean.TRUE.equals(isSuccess)) {
            redisUtil.set(String.format(RedisKey.REDIS_TRANSACTION_SUCCESS.getKey()
                    , rechargeVO.getOrderSn()), 1, RedisKey.REDIS_TRANSACTION_SUCCESS.getTime());
        }

        return Boolean.TRUE.equals(isSuccess);
    }

    @Override
    public TxResultRes txResultCacheQuery(TxResultReq txResultReq) {

        verifyParam(txResultReq.getPayChannel());
        //充值回调
        boolean isSuccess = redisUtil.hasKey(String.format(RedisKey.REDIS_TRANSACTION_SUCCESS.getKey()
                , txResultReq.getOrderSn()));

        if (isSuccess) {
            Integer status = (Integer) redisUtil.get(String.format(RedisKey.REDIS_TRANSACTION_SUCCESS.getKey()
                    , txResultReq.getOrderSn()));
            TxResultRes txResultRes = processCacheResult(txResultReq, status, null);
            if (txResultRes != null) return txResultRes;
        }
        return new TxResultRes(txResultReq.getOrderSn(), StarConstants.Pay_Status.PAY_ING.name());
    }

    @Override
    public TxResultRes txResultQuery(TxResultReq txResultReq) {
        String txResultKey = String.format(RedisKey.REDIS_TRANSACTION_SUCCESS.getKey(), txResultReq.getOrderSn());

        boolean isSuccess = redisUtil.hasKey(txResultKey);
        int status = isSuccess ? (Integer) redisUtil.get(txResultKey) : -1;

        WalletRecordVO walletRecordVO = walletRepository.queryWalletRecordBySerialNo(txResultReq.getOrderSn(), null);

        //缓存命中 处理
        TxResultRes txResultRes = processCacheResult(txResultReq, status, walletRecordVO);
        if (txResultRes != null) return txResultRes;

        //缓存未命中 查库
        if (!isSuccess) {
            //未找到记录 请求超时
            if (null == walletRecordVO) {
                log.error("单号：[{}] 支付请求超时，数据异常", txResultReq.getOrderSn());
                throw new StarException(StarError.REQUEST_TIMEOUT_ERROR);
            }
            return new TxResultRes(walletRecordVO.getRecordSn(), walletRecordVO.getPayStatus());
        }

        return new TxResultRes(txResultReq.getOrderSn(), StarConstants.Pay_Status.PAY_EXCEPTION.name());
    }

    //根据缓存值处理结果
    private TxResultRes processCacheResult(TxResultReq txResultReq, int status, WalletRecordVO walletRecordVO) {
        //缓存命中 返回
        if (status == 1) {
            return new TxResultRes(txResultReq.getOrderSn(), StarConstants.Pay_Status.PAY_SUCCESS.name());
        }
        if (null == walletRecordVO) {
            walletRecordVO = walletRepository.queryWalletRecordBySerialNo(txResultReq.getOrderSn(), null);
        }
        //缓存命中 支付失败
        if (status == 0) {
            Result result = stateHandler.payFailure(txResultReq.getOrderSn(), StarConstants.Pay_Status.valueOf(walletRecordVO.getPayStatus()));
            if (!result.getCode().equals(ResultCode.SUCCESS.getCode())) throw new RuntimeException(result.getInfo());
            return new TxResultRes(txResultReq.getOrderSn(), StarConstants.Pay_Status.PAY_FAILED.name());
        }
        return null;
    }

    @Override
    public boolean cardBind(CardBindReq cardBindReq) {
        return walletRepository.cardBinding(BankRelationVO.builder().uid(cardBindReq.getUid()).cardNo(cardBindReq.getCardNo()).cardType(cardBindReq.getCardType())
                .cardName(cardBindReq.getCardName()).nickname(cardBindReq.getNickname()).isDefault(cardBindReq.getIsDefault()).phone(cardBindReq.getPhone())
                .bankShortName(cardBindReq.getBankShortName()).build());
    }

    @Override
    public List<CardBindResult> obtainCards(Long uid) {
        List<BankRelationVO> relations = walletRepository.queryCardBindings(uid);
        ArrayList<@Nullable CardBindResult> results = Lists.newArrayList();
        for (BankRelationVO relation : relations) {
            results.add(new CardBindResult(relation.getCardNo(), relation.getCardName(), relation.getCardType(),
                    relation.getBankShortName(), relation.getIsDefault()));
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

    @Override
    public WithdrawRecordVO queryWithDrawByTradeNo(String recordSn) {
        return walletRepository.queryWithDrawRecordTradeNo(recordSn);
    }

    @Override
    public boolean doTransaction(TransReq transReq) {

        WalletVO walletVO = walletRepository.queryWallet(new WalletInfoReq(transReq.getUid()));
        if (walletVO == null) {
            throw new RuntimeException("未找到钱包");
        }
        if (!isVerified.get()) {
            //如果是负数 验证余额是否充足
            if (transReq.getPayAmount().signum() == -1 && walletVO.getBalance().compareTo(transReq.getPayAmount().abs()) < 0) {
                throw new StarException(StarError.BALANCE_NOT_ENOUGH);
            }
        }

        BigDecimal curr = walletVO.getBalance().add(transReq.getPayAmount());
        Boolean isSuccess = template.execute(status -> {
            //记录钱包记录
            boolean logWrite = walletRepository.createWalletLog(WalletLogReq.builder().walletId(walletVO.getWalletId())
                    .userId(walletVO.getUid()).offset(transReq.getTotalAmount()).currentMoney(curr).payChannel(transReq.getPayChannel())
                    .orderNo(transReq.getOrderSn()).build());
            //修改余额
            boolean balanceModify = walletRepository.modifyWalletBalance(WalletVO.builder().uid(Long.valueOf(transReq.getUid()))
                    .balance(curr)
                    .wallet_income(transReq.getPayAmount().signum() >= 0 ? walletVO.getWallet_income().add(transReq.getPayAmount()) : null)
                    .wallet_outcome(transReq.getPayAmount().signum() == -1 ? walletVO.getWallet_outcome().add(transReq.getPayAmount()) : null)
                    .build());
            return logWrite && balanceModify;
        });
        return isSuccess;
    }

    @Override
    public boolean doTransactionNoVerify(TransReq transReq) {

        WalletVO walletVO = walletRepository.queryWallet(new WalletInfoReq(transReq.getUid()));
        if (walletVO == null) {
            throw new RuntimeException("未找到钱包");
        }

        BigDecimal curr = walletVO.getBalance().add(transReq.getPayAmount());
        Boolean isSuccess = template.execute(status -> {
            //记录钱包记录
            boolean logWrite = walletRepository.createWalletLog(WalletLogReq.builder().walletId(walletVO.getWalletId())
                    .userId(walletVO.getUid()).offset(transReq.getTotalAmount()).currentMoney(curr).payChannel(transReq.getPayChannel())
                    .orderNo(transReq.getOrderSn()).build());
            //修改余额
            boolean balanceModify = walletRepository.modifyWalletBalance(WalletVO.builder().uid(Long.valueOf(transReq.getUid()))
                    .balance(curr)
                    .wallet_income(transReq.getPayAmount().signum() >= 0 ? walletVO.getWallet_income().add(transReq.getPayAmount()) : null)
                    .wallet_outcome(transReq.getPayAmount().signum() == -1 ? walletVO.getWallet_outcome().add(transReq.getPayAmount()) : null)
                    .build());
            return logWrite && balanceModify;
        });
        return isSuccess;
    }

    @Override
    public boolean doC2BTransaction(TransReq transReq) {
        WalletVO walletVO = walletRepository.queryWallet(new WalletInfoReq(transReq.getUid()));
        if (walletVO == null) {
            throw new RuntimeException("未找到钱包");
        }
        BigDecimal curr = walletVO.getBalance().add(transReq.getPayAmount());
        Boolean isSuccess = template.execute(status -> {
            //记录钱包记录
            boolean logWrite = walletRepository.createWalletLog(WalletLogReq.builder().walletId(walletVO.getWalletId())
                    .userId(walletVO.getUid()).offset(transReq.getTotalAmount()).currentMoney(curr).payChannel(transReq.getPayChannel())
                    .orderNo(transReq.getOrderSn()).build());
            //修改余额
            boolean balanceModify = walletRepository.modifyWalletBalance(WalletVO.builder().uid(Long.valueOf(transReq.getUid()))
                    .balance(curr)
                    .wallet_income(transReq.getPayAmount().signum() >= 0 ? walletVO.getWallet_income().add(transReq.getPayAmount()) : null)
                    .wallet_outcome(transReq.getPayAmount().signum() == -1 ? walletVO.getWallet_outcome().add(transReq.getPayAmount()) : null)
                    .build());
            return logWrite && balanceModify;
        });
        return isSuccess;
    }

    @Override
    public ReceivablesCalculateResult ReceivablesMoneyCalculate(CalculateReq calculate) {

        verifyParam(calculate.getChannel());
        WalletConfigVO config = WalletConfig.getConfig(StarConstants.PayChannel.valueOf(calculate.getChannel()));
        //交易手续费
        BigDecimal transRate = calculate.getMoney().multiply(config.getServiceRate());
        //版权费
        BigDecimal copyrightRate = calculate.getMoney().multiply(config.getCopyrightRate());
        BigDecimal calculated = calculate.getMoney().subtract(transRate).subtract(copyrightRate);
        NumberFormat percent = NumberFormat.getPercentInstance();
        NumberFormat number = NumberFormat.getNumberInstance();
        number.setMaximumFractionDigits(3);
        percent.setMaximumFractionDigits(4);
        return new ReceivablesCalculateResult(calculated, percent.format(config.getServiceRate()),
                percent.format(config.getCopyrightRate()), transRate, copyrightRate);

    }

    @Override
    public boolean feeProcess(String recordSn, BigDecimal fee) {
        return walletRepository.updateWalletRecordFee(recordSn,fee);
    }

    @Override
    public List<String> checkPay() {
        List<WalletVO> walletVOS = walletRepository.selectAllWallet();

        List<List<WalletVO>> partition = Lists.partition(walletVOS, 30);


        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<String> exUsers = Lists.newArrayList();
        for (List<WalletVO> vos : partition) {
            ForkJoinTask<List<String>> submit = forkJoinPool.submit(() -> {
                List<String> exCollection = Lists.newArrayList();
                for (WalletVO vo : vos) {
                    List<WalletLogVO> walletLogVOS = walletRepository.usersBill(vo.getUid());
                    String uid = verifyData(walletLogVOS, vo);
                    if (verifyData(walletLogVOS, vo) != null) {
                        exCollection.add(uid);
                    }
                }
                return exCollection;
            });

            try {
                List<String> exUserIds = submit.get();
                exUsers.addAll(exUserIds);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        if (CollectionUtil.isEmpty(exUsers)) {
            return null;
        }
        return exUsers;
    }

    private String verifyData(List<WalletLogVO> walletLogVOS, WalletVO vo) {
        BigDecimal value = BigDecimal.ZERO;
        for (WalletLogVO walletLogVO : walletLogVOS) {
            if (walletLogVO.getRecordSn().contains(StarConstants.OrderPrefix.WithdrawSn.getPrefix())
                    || walletLogVO.getRecordSn().contains(StarConstants.OrderPrefix.PublishGoods.getPrefix())) {
                value = value.add(walletLogVO.getBalanceOffset().negate());
            }
            if (walletLogVO.getRecordSn().contains(StarConstants.OrderPrefix.TransactionSn.getPrefix())) {

            }
            if (walletLogVO.getRecordSn().contains(StarConstants.OrderPrefix.RechargeSn.getPrefix())) {
                value = value.add(walletLogVO.getBalanceOffset().abs());
            }
        }

        if (!value.equals(vo.getBalance())) {
            return vo.getUid().toString();
        }

        return null;
    }


}
