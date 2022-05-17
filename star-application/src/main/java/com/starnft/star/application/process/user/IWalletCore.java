package com.starnft.star.application.process.user;

import com.starnft.star.application.process.user.req.PayRecordReq;
import com.starnft.star.application.process.user.req.RechargeFacadeReq;
import com.starnft.star.application.process.user.res.TransactionRecord;
import com.starnft.star.application.process.user.res.RechargeReqResult;
import com.starnft.star.common.page.ResponsePageResult;

import javax.validation.Valid;

public interface IWalletCore {


    //充值
    RechargeReqResult recharge(RechargeFacadeReq rechargeFacadeReq);

    //充值回调 更新钱包余额 和钱包变动以及状态


    //交易记录查询【带类型筛选】
    ResponsePageResult<TransactionRecord> walletRecordQuery(@Valid PayRecordReq recordReq);

}
