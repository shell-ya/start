package com.starnft.star.infrastructure.repository;

import com.starnft.star.domain.wallet.model.req.RechargeReq;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.vo.WalletVO;
import com.starnft.star.domain.wallet.repository.IWalletRepository;
import com.starnft.star.infrastructure.entity.wallet.StarNftWalletLog;
import com.starnft.star.infrastructure.entity.wallet.StarNftWalletRecord;
import com.starnft.star.infrastructure.entity.wallet.Wallet;
import com.starnft.star.infrastructure.mapper.wallet.StarNftWalletLogMapper;
import com.starnft.star.infrastructure.mapper.wallet.WalletMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Repository
public class WalletRepository implements IWalletRepository {

    @Resource
    private WalletMapper walletMapper;

    @Resource
    private StarNftWalletLogMapper starNftWalletLogMapper;

    @Override
    public WalletVO queryWallet(WalletInfoReq walletInfoReq) {
        Wallet wallet = new Wallet();
        wallet.setUid(walletInfoReq.getUid());

        Wallet walletInfo = walletMapper.queryWalletByParams(wallet);

        if (null == walletInfo) {
            return null;
        }

        WalletVO walletVO = WalletVO.builder().walletId(walletInfo.getwId())
                .balance(walletInfo.getBalance())
                .frozen(getStatus(walletInfo.getFrozen()))
                .frozen_fee(walletInfo.getFrozenFee())
                .wallet_income(walletInfo.getWalletIncome())
                .wallet_outcome(walletInfo.getWalletOutcome()).build();
        return walletVO;
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
    public Integer createWalletLog(RechargeReq rechargeReq) {
        StarNftWalletLog starNftWalletLog = initWalletLog(rechargeReq);
        return starNftWalletLogMapper.createChargeLog(starNftWalletLog);
    }

    @Override
    public Integer createWalletRecord(RechargeReq rechargeReq) {
        StarNftWalletRecord starNftWalletRecord = initWalletRecord(rechargeReq);
        return null;
    }

    private StarNftWalletRecord initWalletRecord(RechargeReq rechargeReq) {
        return null;
    }

    private Wallet initWallet(WalletInfoReq walletInfoReq) {
        Wallet wallet = new Wallet();
        wallet.setUid(walletInfoReq.getUid());
        wallet.setwId(walletInfoReq.getWalletId());
        wallet.setCreatedBy(walletInfoReq.getUid());
        wallet.setBalance(new BigDecimal("0.00"));
        wallet.setFrozen("0");
        wallet.setWalletIncome(new BigDecimal("0.00"));
        wallet.setWalletOutcome(new BigDecimal("0.00"));
        wallet.setFrozenFee(new BigDecimal("0.00"));
        wallet.setCreatedAt(new Date());
        return wallet;
    }

    private StarNftWalletLog initWalletLog(RechargeReq rechargeReq) {
        StarNftWalletLog starNftWalletLog = new StarNftWalletLog();
        starNftWalletLog.setUid(rechargeReq.getUserId());
        starNftWalletLog.setwId(rechargeReq.getWalletId());
        starNftWalletLog.setBalanceOffset(rechargeReq.getMoney());
        starNftWalletLog.setCurrentBalance(rechargeReq.getCurrentMoney());
        starNftWalletLog.setRecordSn(rechargeReq.getPayNo());
        starNftWalletLog.setIsDeleted(false);
        starNftWalletLog.setDisplay(0);
        starNftWalletLog.setCreatedAt(new Date());
        starNftWalletLog.setCreatedBy(rechargeReq.getUserId());
        starNftWalletLog.setChannel(rechargeReq.getPayChannel());

        return starNftWalletLog;
    }

    private boolean getStatus(String code) {
        return Integer.parseInt(code) == 1;
    }
}
