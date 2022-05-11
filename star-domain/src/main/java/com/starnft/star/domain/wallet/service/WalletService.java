package com.starnft.star.domain.wallet.service;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.wallet.model.req.TransactionRecordQueryReq;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.req.WalletRecordReq;
import com.starnft.star.domain.wallet.model.res.WalletResult;
import com.starnft.star.domain.wallet.model.vo.WalletRecordVO;

public interface WalletService {

    //钱包余额查询
    WalletResult queryWalletInfo(WalletInfoReq walletInfoReq);

    //生成预充值记录
    boolean rechargeRecordGenerate(WalletRecordReq walletRecordReq);

    //todo 提现

    //查询充值记录
    ResponsePageResult<WalletRecordVO> queryTransactionRecord(TransactionRecordQueryReq queryReq);
    //查询提现记录

    //查询账单汇总


}
