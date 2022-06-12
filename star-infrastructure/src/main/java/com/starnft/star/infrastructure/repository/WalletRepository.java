package com.starnft.star.infrastructure.repository;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.starnft.star.common.constant.DatePattern;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.common.utils.DateUtil;
import com.starnft.star.common.utils.secure.AESUtil;
import com.starnft.star.domain.wallet.model.req.RechargeReq;
import com.starnft.star.domain.wallet.model.req.TransactionRecordQueryReq;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.req.WalletRecordReq;
import com.starnft.star.domain.wallet.model.vo.*;
import com.starnft.star.domain.wallet.repository.IWalletRepository;
import com.starnft.star.infrastructure.entity.wallet.*;
import com.starnft.star.infrastructure.mapper.wallet.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class WalletRepository implements IWalletRepository {

    @Resource
    private WalletMapper walletMapper;
    @Resource
    private StarNftWalletLogMapper starNftWalletLogMapper;
    @Resource
    private StarNftWalletRecordMapper starNftWalletRecordMapper;
    @Resource
    private StarNftWalletConfigMapper starNftWalletConfigMapper;
    @Resource
    private StarNftWithdrawApplyMapper starNftWithdrawApplyMapper;

    @Resource
    private StarNftBankRelationMapper starNftBankRelationMapper;

    @Override
    public WalletVO queryWallet(WalletInfoReq walletInfoReq) {
        Wallet walletInfo = getWallet(walletInfoReq);

        if (null == walletInfo) {
            return null;
        }

        return WalletVO.builder()
                .uid(walletInfo.getUid())
                .walletId(walletInfo.getwId())
                .balance(walletInfo.getBalance())
                .frozen(walletInfo.getFrozen() == 1)
                .frozen_fee(walletInfo.getFrozenFee())
                .wallet_income(walletInfo.getWalletIncome())
                .wallet_outcome(walletInfo.getWalletOutcome()).build();
    }

    /**
     * 获取钱包信息po
     *
     * @param walletInfoReq
     * @return
     */
    private Wallet getWallet(WalletInfoReq walletInfoReq) {
        Wallet wallet = new Wallet();
        wallet.setUid(walletInfoReq.getUid());

        return walletMapper.queryWalletByParams(wallet);
    }

    @Override
    @Transactional
    public WalletVO createWallet(WalletInfoReq walletInfoReq) {
        Wallet wallet = initWallet(walletInfoReq);

        walletMapper.createWallet(wallet);

        WalletVO walletVO = WalletVO.builder()
                .walletId(wallet.getwId())
                .balance(new BigDecimal(BigDecimal.ZERO.intValue()))
                .frozen(false)
                .frozen_fee(new BigDecimal(BigDecimal.ZERO.intValue()))
                .wallet_income(new BigDecimal(BigDecimal.ZERO.intValue()))
                .wallet_outcome(new BigDecimal(BigDecimal.ZERO.intValue())).build();
        return walletVO;
    }

    @Override
    @Transactional
    public boolean createWalletLog(RechargeReq rechargeReq) {
        StarNftWalletLog starNftWalletLog = initWalletLog(rechargeReq);
        return starNftWalletLogMapper.createChargeLog(starNftWalletLog) == 1;
    }

    @Override
    @Transactional
    public boolean modifyWalletBalance(WalletVO walletVO) {
        Wallet wallet = new Wallet();

        wallet.setUid(walletVO.getUid());
        wallet.setwId(walletVO.getWalletId());
        wallet.setBalance(walletVO.getBalance());
        wallet.setWalletOutcome(walletVO.getWallet_outcome());
        wallet.setWalletIncome(walletVO.getWallet_income());
        wallet.setFrozen(walletVO.isFrozen() ? 1 : 0);
        wallet.setFrozenFee(walletVO.getFrozen_fee());

        Integer isSuccess = walletMapper.updateWallet(wallet);

        return isSuccess == 1;
    }

    @Override
    @Transactional
    public boolean createWalletRecord(WalletRecordReq walletRecordReq) {
        StarNftWalletRecord starNftWalletRecord = initWalletRecord(walletRecordReq);
        return starNftWalletRecordMapper.createWalletRecord(starNftWalletRecord) == 1;
    }

    @Override
    public List<WalletConfigVO> loadAllWalletConfig() {
        List<StarNftWalletConfig> starNftWalletConfigs = starNftWalletConfigMapper.loadAllWalletConfig();

        List<WalletConfigVO> configs = Lists.newArrayList();
        for (StarNftWalletConfig config : starNftWalletConfigs) {
            WalletConfigVO walletConfigVO = WalletConfigVO.builder().chargeRate(config.getChargeRate())
                    .stableRate(config.getStableRate())
                    .channel(StarConstants.PayChannel.valueOf(config.getChannel()))
                    .identityCode(config.getIdentityCode())
                    .rechargeLimit(config.getRechargeLimit())
                    .copyrightRate(config.getCopyrightRate())
                    .serviceRate(config.getServiceRate())
                    .withdrawTimes(config.getWithdrawTimes())
                    .withdrawLimit(config.getWithdrawLimit())
                    .build();
            configs.add(walletConfigVO);
        }

        return configs;
    }

    @Override
    public WalletRecordVO queryWalletRecordBySerialNo(String serialNo, String payStatus) {

        StarNftWalletRecord record = queryWalletRecordPO(serialNo, payStatus);

        if (null == record) {
            return null;
        }
        return walletRecordToVO(record);
    }

    @Override
    public List<WalletRecordVO> queryWalletRecordIsPaying(Long uid, Integer tsType, String payStatus) {
        LambdaQueryWrapper<StarNftWalletRecord> queryWrapper = new LambdaQueryWrapper<>();

        LambdaQueryWrapper<StarNftWalletRecord> eq = queryWrapper.eq(Objects.isNull(uid), StarNftWalletRecord::getFromUid, uid)
                .eq(Objects.isNull(tsType), StarNftWalletRecord::getTsType, tsType)
                .eq(StringUtils.isBlank(payStatus), StarNftWalletRecord::getPayStatus, payStatus);

        List<StarNftWalletRecord> starNftWalletRecords = starNftWalletRecordMapper.selectList(eq);

        List<WalletRecordVO> walletRecordVOS = null;
        for (StarNftWalletRecord starNftWalletRecord : starNftWalletRecords) {
            walletRecordVOS = BeanColverUtil.colverList(starNftWalletRecords, WalletRecordVO.class);
        }

        return walletRecordVOS;
    }

    @Override
    public List<WithdrawRecordVO> usersWithdrawRecords(Long uid) {
        List<StarNftWithdrawApply> starNftWithdrawApplies = starNftWithdrawApplyMapper.selectList(new LambdaQueryWrapper<StarNftWithdrawApply>()
                .eq(Objects.isNull(uid), StarNftWithdrawApply::getWithdrawUid, uid));
        List<WithdrawRecordVO> withdrawRecordVOS = Lists.newArrayList();
        for (StarNftWithdrawApply apply : starNftWithdrawApplies) {
            WithdrawRecordVO.builder().uid(uid).withdrawTradeNo(apply.getWithdrawTradeNo())
                    .status(apply.getApplyStatus()).money(apply.getWithdrawMoney())
                    .bankNo(apply.getWithdrawBankNo()).channel(apply.getWithdrawChannel()).build();
        }
        return withdrawRecordVOS;
    }

    @Override
    public boolean updateWithdrawApply(String applySn, Integer status) {
        StarNftWithdrawApply apply = new StarNftWithdrawApply();
        apply.setApplyStatus(status);
        return starNftWithdrawApplyMapper.update(apply, new LambdaQueryWrapper<StarNftWithdrawApply>()
                .eq(Objects.nonNull(applySn), StarNftWithdrawApply::getWithdrawTradeNo, applySn)) == 1;
    }

    private StarNftWalletRecord queryWalletRecordPO(String serialNo, String payStatus) {
        StarNftWalletRecord request = new StarNftWalletRecord();
        request.setRecordSn(serialNo);
        request.setPayStatus(payStatus);

        List<StarNftWalletRecord> starNftWalletRecords = starNftWalletRecordMapper.selectByLimit(request);
        if (starNftWalletRecords.size() > 1) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR);
        }
        if (CollectionUtil.isEmpty(starNftWalletRecords)) {
            return null;
        }

        return starNftWalletRecords.get(0);
    }

    @Override
    @Transactional
    public boolean updateWalletRecordStatus(String serialNo, String payStatus) {

        StarNftWalletRecord record = queryWalletRecordPO(serialNo, null);
        if (null == record) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "记录不存在");
        }

        StarNftWalletRecord update = new StarNftWalletRecord();
        update.setRecordSn(serialNo);
        update.setPayStatus(payStatus);
        Integer integer = starNftWalletRecordMapper.updateRecord(update);

        return integer == 1;
    }

    @Override
    public boolean updateWalletRecordSuccess(String serialNo, String outTradeNo, String payStatus) {
        StarNftWalletRecord record = queryWalletRecordPO(serialNo, null);
        if (null == record) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "记录不存在");
        }

        LambdaQueryWrapper<StarNftWalletRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(serialNo), StarNftWalletRecord::getRecordSn, serialNo);
        record.setOutTradeNo(outTradeNo);
        record.setPayStatus(payStatus);
        return starNftWalletRecordMapper.update(record, wrapper) == 1;
    }

    @Override
    public ResponsePageResult<WalletRecordVO> queryTransactionRecordByCondition(TransactionRecordQueryReq queryReq) {
        PageInfo<StarNftWalletRecord> starNftWalletRecords = PageHelper.startPage(queryReq.getPage(), queryReq.getSize())
                .doSelectPageInfo(() -> starNftWalletRecordMapper.queryRecordOnCondition(queryReq));

        return new ResponsePageResult<WalletRecordVO>(starNftWalletRecords.getList()
                .stream()
                .map(this::walletRecordToVO)
                .collect(Collectors.toList()),
                queryReq.getPage(), queryReq.getSize(), starNftWalletRecords.getTotal());
    }

    @Override
    public boolean createWithdrawRecord(WithdrawRecordVO withdrawRecordVO) {
        StarNftWithdrawApply starNftWithdrawApply = new StarNftWithdrawApply();
        starNftWithdrawApply.setApplyStatus(0);//0 提现中 1 已提现
        starNftWithdrawApply.setApplyTime(DateUtil.getCurrentDateTime(DatePattern.YYYY_MM_DD_HH_MM_SS));
        starNftWithdrawApply.setWithdrawBankNo(withdrawRecordVO.getBankNo());
        starNftWithdrawApply.setBankMatchName(withdrawRecordVO.getCardName());
        starNftWithdrawApply.setWithdrawChannel(withdrawRecordVO.getChannel());
        starNftWithdrawApply.setWithdrawMoney(withdrawRecordVO.getMoney());
        starNftWithdrawApply.setWithdrawUid(withdrawRecordVO.getUid());
        starNftWithdrawApply.setWithdrawTradeNo(withdrawRecordVO.getWithdrawTradeNo());
        starNftWithdrawApply.setCreatedBy(withdrawRecordVO.getUid());
        starNftWithdrawApply.setCreatedAt(new Date());
        starNftWithdrawApply.setIsDeleted(false);
        return starNftWithdrawApplyMapper.insertRecord(starNftWithdrawApply);
    }

    @Override
    @Transactional
    public boolean cardBinding(BankRelationVO bankRelationVO) {
        StarNftBankRelation starNftBankRelation = new StarNftBankRelation();
        List<BankRelationVO> relationVOS = queryCardBindings(bankRelationVO.getUid());
        starNftBankRelation.setCardNo(AESUtil.encrypt(bankRelationVO.getCardNo()));
        starNftBankRelation.setUid(bankRelationVO.getUid());
        List<StarNftBankRelation> starNftBankRelations = starNftBankRelationMapper.queryByCondition(starNftBankRelation);
        //卡是否已绑定
        if (starNftBankRelations.size() >= 1) {
            throw new StarException(StarError.CARD_HAS_BIND);
        }
        //是否还未绑卡 则设置为默认卡
        if (relationVOS.size() == 0) {
            starNftBankRelation.setIsDefault(1);
        } else {
            starNftBankRelation.setIsDefault(bankRelationVO.getIsDefault());
        }
        starNftBankRelation.setNickname(bankRelationVO.getNickname());
        starNftBankRelation.setCardName(bankRelationVO.getCardName());
        starNftBankRelation.setCardType(bankRelationVO.getCardType());
        starNftBankRelation.setBankNameShort(bankRelationVO.getBankShortName());
        starNftBankRelation.setCreatedBy(bankRelationVO.getUid());
        starNftBankRelation.setPhone(bankRelationVO.getPhone());
        starNftBankRelation.setCreatedAt(new Date());
        return starNftBankRelationMapper.insert(starNftBankRelation) == 1;
    }

    @Override
    public List<BankRelationVO> queryCardBindings(Long uid) {
        StarNftBankRelation starNftBankRelation = new StarNftBankRelation();
        starNftBankRelation.setUid(uid);
        List<StarNftBankRelation> starNftBankRelations = starNftBankRelationMapper.queryByCondition(starNftBankRelation);
        ArrayList<@Nullable BankRelationVO> relations = Lists.newArrayList();
        for (StarNftBankRelation nftBankRelation : starNftBankRelations) {
            relations.add(BankRelationVO.builder()
                    .uid(uid)
                    .cardNo(AESUtil.decrypt(nftBankRelation.getCardNo()))
                    .cardType(nftBankRelation.getCardType())
                    .cardName(nftBankRelation.getCardName())
                    .isDefault(nftBankRelation.getIsDefault())
                    .bankShortName(nftBankRelation.getBankNameShort()).build());
        }

        return relations;
    }

    @Override
    @Transactional
    public boolean deleteCard(List<BankRelationVO> bankRelations) {

        boolean hasDefault = false;
        for (BankRelationVO bankRelation : bankRelations) {
            if (bankRelation.getIsDefault() == 1) {
                hasDefault = true;
            }
            starNftBankRelationMapper.delete(bankRelation.getUid(), AESUtil.encrypt(bankRelation.getCardNo()));
        }
        List<BankRelationVO> relationVOS = queryCardBindings(bankRelations.get(0).getUid());

        boolean hasCards = relationVOS.size() > 0;
        if (!hasCards) {
            return true;
        }

        //删除的卡中有默认的卡时则 将剩余未被删除的卡中随机选择一个卡作为默认卡 全部删除则直接返回
        BankRelationVO bankRelationVO = relationVOS.get(0);
        bankRelationVO.setIsDefault(1);

        return setDefaultCard(bankRelationVO);

    }

    @Override
    @Transactional
    public boolean setDefaultCard(BankRelationVO relationVO) {
        StarNftBankRelation starNftBankRelation = new StarNftBankRelation();
        starNftBankRelation.setIsDefault(1);
        starNftBankRelation.setUid(relationVO.getUid());
        List<StarNftBankRelation> starNftBankRelations = starNftBankRelationMapper.queryByCondition(starNftBankRelation);
        if (starNftBankRelations.size() > 1) {
            log.error("[{}]默认银行卡数量大于1 出现异常", relationVO.getUid());
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR);
        }

        if (starNftBankRelations.size() != 0) {
            StarNftBankRelation defaultRelation = starNftBankRelations.get(0);
            defaultRelation.setModifiedAt(new Date());
            defaultRelation.setModifiedBy(relationVO.getUid());
            defaultRelation.setCardNo(defaultRelation.getCardNo());
            defaultRelation.setIsDefault(0);
            Integer reSet = starNftBankRelationMapper.update(defaultRelation);
            if (reSet != 1) {
                throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR);
            }
        }

        starNftBankRelation.setCardNo(AESUtil.encrypt(relationVO.getCardNo()));
        Integer updated = starNftBankRelationMapper.update(starNftBankRelation);

        return updated == 1;
    }


    private WalletRecordVO walletRecordToVO(StarNftWalletRecord record) {
        return WalletRecordVO.builder().recordSn(record.getRecordSn())
                .orderSn(record.getOrderSn())
                .outTradeNo(record.getOutTradeNo())
                .fromUid(record.getFromUid())
                .payChannel(record.getPayChannel())
                .payTime(record.getPayTime())
                .payStatus(record.getPayStatus())
                .toUid(record.getToUid())
                .remark(record.getRemark())
                .tsMoney(record.getTsMoney())
                .tsType(record.getTsType()).build();
    }

    /**
     * 填充钱包交易记录实体
     *
     * @param walletRecordReq 交易记录信息
     * @return 交易记录实体
     */
    private StarNftWalletRecord initWalletRecord(WalletRecordReq walletRecordReq) {
        StarNftWalletRecord starNftWalletRecord = new StarNftWalletRecord();
        starNftWalletRecord.setRecordSn(walletRecordReq.getRecordSn());
        starNftWalletRecord.setFromUid(walletRecordReq.getFrom_uid());
        starNftWalletRecord.setToUid(walletRecordReq.getTo_uid());
        starNftWalletRecord.setPayStatus(walletRecordReq.getPayStatus());
        starNftWalletRecord.setTsType(walletRecordReq.getTsType());
        starNftWalletRecord.setTsMoney(walletRecordReq.getTsMoney());
        starNftWalletRecord.setPayChannel(walletRecordReq.getPayChannel());
        starNftWalletRecord.setPayTime(walletRecordReq.getPayTime());
        starNftWalletRecord.setCreatedBy(walletRecordReq.getFrom_uid() == 0 ? walletRecordReq.getTo_uid() : walletRecordReq.getFrom_uid());
        starNftWalletRecord.setCreatedAt(new Date());
        starNftWalletRecord.setIsDeleted(false);
        return starNftWalletRecord;
    }

    /**
     * 填充钱包信息实体
     *
     * @param walletInfoReq 钱包信息
     * @return 钱包实体
     */
    private Wallet initWallet(WalletInfoReq walletInfoReq) {
        Wallet wallet = new Wallet();
        wallet.setUid(walletInfoReq.getUid());
        wallet.setwId(walletInfoReq.getWalletId());
        wallet.setCreatedBy(walletInfoReq.getUid());
        wallet.setBalance(new BigDecimal(BigDecimal.ZERO.intValue()));
        wallet.setFrozen(0);
        wallet.setWalletIncome(new BigDecimal(BigDecimal.ZERO.intValue()));
        wallet.setWalletOutcome(new BigDecimal(BigDecimal.ZERO.intValue()));
        wallet.setFrozenFee(new BigDecimal(BigDecimal.ZERO.intValue()));
        wallet.setCreatedAt(new Date());
        return wallet;
    }

    /**
     * 填充钱包变化记录实体
     *
     * @param rechargeReq 钱包变化记录信息
     * @return 钱包变化记录实体
     */
    private StarNftWalletLog initWalletLog(RechargeReq rechargeReq) {
        StarNftWalletLog starNftWalletLog = new StarNftWalletLog();
        starNftWalletLog.setUid(rechargeReq.getUserId());
        starNftWalletLog.setwId(rechargeReq.getWalletId());
        starNftWalletLog.setBalanceOffset(rechargeReq.getMoney());
        starNftWalletLog.setCurrentBalance(rechargeReq.getCurrentMoney());
        starNftWalletLog.setRecordSn(rechargeReq.getPayNo());
        starNftWalletLog.setDisplay(0);
        starNftWalletLog.setCreatedAt(new Date());
        starNftWalletLog.setCreatedBy(rechargeReq.getUserId());
        starNftWalletLog.setChannel(rechargeReq.getPayChannel());

        return starNftWalletLog;
    }

}
