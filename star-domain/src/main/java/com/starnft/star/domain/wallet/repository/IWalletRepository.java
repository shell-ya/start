package com.starnft.star.domain.wallet.repository;

import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.vo.WalletVO;

public interface IWalletRepository {

    /**
     *  根据uid获取钱包信息
     * @param walletInfoReq 获取钱包信息请求
     * @return WalletVO
     */
    WalletVO queryWallet(WalletInfoReq walletInfoReq);

    WalletVO createWallet(WalletInfoReq walletInfoReq);
}
