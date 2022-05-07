package com.starnft.star.domain.wallet.service.impl;

import com.starnft.star.common.utils.WalletAddrGenerator;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.res.WalletResult;
import com.starnft.star.domain.wallet.model.vo.WalletVO;
import com.starnft.star.domain.wallet.repository.IWalletRepository;
import com.starnft.star.domain.wallet.service.WalletService;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class WalletServiceImpl implements WalletService {

    @Resource
    private IWalletRepository walletRepository;

    @Resource
    private WalletAddrGenerator walletAddrGenerator;

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

}
