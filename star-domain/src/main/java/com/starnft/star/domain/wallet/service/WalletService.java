package com.starnft.star.domain.wallet.service;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.wallet.model.req.TransactionRecordQueryReq;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.req.WalletRecordReq;
import com.starnft.star.domain.wallet.model.req.WithDrawReq;
import com.starnft.star.domain.wallet.model.res.WalletResult;
import com.starnft.star.domain.wallet.model.res.WithdrawResult;
import com.starnft.star.domain.wallet.model.vo.WalletRecordVO;

public interface WalletService {

    //钱包余额查询
    WalletResult queryWalletInfo(WalletInfoReq walletInfoReq);

    //生成预充值记录
    boolean rechargeRecordGenerate(WalletRecordReq walletRecordReq);

    //todo 提现
    WithdrawResult withdraw(WithDrawReq withDrawReq);

    //查询交易记录
    ResponsePageResult<WalletRecordVO> queryTransactionRecord(TransactionRecordQueryReq queryReq);

    //支付成功 回调修改钱包余额 变动以及状态




}
