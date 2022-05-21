package com.starnft.star.application.process.wallet;

import com.starnft.star.application.process.wallet.req.PayRecordReq;
import com.starnft.star.application.process.wallet.req.RechargeFacadeReq;
import com.starnft.star.domain.wallet.model.req.WithDrawReq;
import com.starnft.star.application.process.wallet.res.RechargeReqResult;
import com.starnft.star.application.process.wallet.res.TransactionRecord;
import com.starnft.star.domain.wallet.model.res.WithdrawResult;
import com.starnft.star.common.page.ResponsePageResult;

public interface IWalletCore {


    //充值
    RechargeReqResult recharge(RechargeFacadeReq rechargeFacadeReq);

    //充值回调 更新钱包余额 和钱包变动以及状态


    //交易记录查询【带类型筛选】
    ResponsePageResult<TransactionRecord> walletRecordQuery(PayRecordReq recordReq);


    //提现
    WithdrawResult withdraw(WithDrawReq withDrawReq);

}
