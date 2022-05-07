package com.starnft.star.domain.wallet.service;

import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.res.WalletResult;

public interface WalletService {

    //钱包余额查询
    WalletResult queryWalletInfo(WalletInfoReq walletInfoReq);
    //充值回调

    //提现

    //查询充值记录

    //查询提现记录

    //查询账单汇总

    //查询账单详情

}
