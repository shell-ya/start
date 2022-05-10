package com.starnft.star.infrastructure.repository;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.wallet.model.req.RechargeReq;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.req.WalletRecordReq;
import com.starnft.star.domain.wallet.model.vo.WalletConfigVO;
import com.starnft.star.domain.wallet.model.vo.WalletRecordVO;
import com.starnft.star.domain.wallet.model.vo.WalletVO;
import com.starnft.star.domain.wallet.repository.IWalletRepository;
import com.starnft.star.infrastructure.entity.wallet.StarNftWalletConfig;
import com.starnft.star.infrastructure.entity.wallet.StarNftWalletLog;
import com.starnft.star.infrastructure.entity.wallet.StarNftWalletRecord;
import com.starnft.star.infrastructure.entity.wallet.Wallet;
import com.starnft.star.infrastructure.mapper.wallet.StarNftWalletConfigMapper;
import com.starnft.star.infrastructure.mapper.wallet.StarNftWalletLogMapper;
import com.starnft.star.infrastructure.mapper.wallet.StarNftWalletRecordMapper;
import com.starnft.star.infrastructure.mapper.wallet.WalletMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public class WalletRepository implements IWalletRepository {

    @Resource
    private WalletMapper walletMapper;
    @Resource
    private StarNftWalletLogMapper starNftWalletLogMapper;
    @Resource
    private StarNftWalletRecordMapper starNftWalletRecordMapper;
    @Resource
    private StarNftWalletConfigMapper starNftWalletConfigMapper;

    @Override
    public WalletVO queryWallet(WalletInfoReq walletInfoReq) {
        Wallet walletInfo = getWallet(walletInfoReq);

        if (null == walletInfo) {
            return null;
        }

        return WalletVO.builder().walletId(walletInfo.getwId())
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
                .balance(new BigDecimal("0.00"))
                .frozen(false)
                .frozen_fee(new BigDecimal("0.00"))
                .wallet_income(new BigDecimal("0.00"))
                .wallet_outcome(new BigDecimal("0.00")).build();
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
    public boolean modifyWalletBalance(RechargeReq rechargeReq) {
        Wallet wallet = getWallet(new WalletInfoReq(rechargeReq.getWalletId(), rechargeReq.getUserId()));

        //正数代表收入
        if (rechargeReq.getMoney().signum() >= 0) {
            wallet.setBalance(wallet.getWalletIncome().add(rechargeReq.getMoney()));
            wallet.setWalletIncome(wallet.getWalletIncome().add(rechargeReq.getMoney()));
        } else {
            //校验钱包余额
            verifyWalletBalance(wallet, rechargeReq);

            wallet.setBalance(wallet.getWalletIncome().subtract(rechargeReq.getMoney()));
            wallet.setWalletOutcome(wallet.getWalletOutcome().subtract(rechargeReq.getMoney().abs()));
        }

        Integer isSuccess = walletMapper.updateWallet(wallet);

        return isSuccess == 1;
    }

    private void verifyWalletBalance(Wallet wallet, RechargeReq rechargeReq) {
        //资金未被冻结
        if (wallet.getFrozen() == 0 && wallet.getBalance()
                .subtract(rechargeReq.getMoney().abs()).signum() == -1) {
            throw new StarException(StarError.BALANCE_NOT_ENOUGH, "钱包余额不足");
        }
        // 有被冻结资金
        if (wallet.getFrozen() == 1 && wallet.getBalance()
                .subtract(rechargeReq.getMoney().abs()).subtract(wallet.getFrozenFee().abs()).signum() == -1) {
            throw new StarException(StarError.BALANCE_NOT_ENOUGH, "可能部分资金被冻结导致余额不足!");
        }

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

        StarNftWalletRecord record = queryWalletRecordPO(serialNo, payStatus);
        if (null == record) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "记录不存在");
        }

        StarNftWalletRecord update = new StarNftWalletRecord();
        update.setRecordSn(serialNo);
        update.setPayStatus(payStatus);
        Integer integer = starNftWalletRecordMapper.updateRecord(update);

        return integer == 1;
    }

    private WalletRecordVO walletRecordToVO(StarNftWalletRecord record) {
        return WalletRecordVO.builder().recordSn(record.getRecordSn())
                .checkStatus(record.getCheckStatus())
                .checkTime(record.getCheckTime())
                .fetchStatus(record.getFetchStatus())
                .fetchTime(record.getFetchTime())
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
        starNftWalletRecord.setPayChannel(walletRecordReq.getPayChannel());
        starNftWalletRecord.setPayTime(walletRecordReq.getPayTime());
        starNftWalletRecord.setCheckStatus(walletRecordReq.getCheckStatus());
        starNftWalletRecord.setCheckTime(walletRecordReq.getCheckTime());
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
        wallet.setBalance(new BigDecimal("0.00"));
        wallet.setFrozen(0);
        wallet.setWalletIncome(new BigDecimal("0.00"));
        wallet.setWalletOutcome(new BigDecimal("0.00"));
        wallet.setFrozenFee(new BigDecimal("0.00"));
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
