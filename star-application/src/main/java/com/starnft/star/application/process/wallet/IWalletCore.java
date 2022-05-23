package com.starnft.star.application.process.wallet;

import com.starnft.star.application.process.wallet.req.PayRecordReq;
import com.starnft.star.application.process.wallet.req.RechargeFacadeReq;
import com.starnft.star.application.process.wallet.res.RechargeReqResult;
import com.starnft.star.application.process.wallet.res.TransactionRecord;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.wallet.model.req.CardBindReq;
import com.starnft.star.domain.wallet.model.req.WithDrawReq;
import com.starnft.star.domain.wallet.model.res.CardBindResult;
import com.starnft.star.domain.wallet.model.res.WithdrawResult;

import java.util.List;

public interface IWalletCore {


    //充值
    RechargeReqResult recharge(RechargeFacadeReq rechargeFacadeReq);

    //充值回调 更新钱包余额 和钱包变动以及状态


    //交易记录查询【带类型筛选】
    ResponsePageResult<TransactionRecord> walletRecordQuery(PayRecordReq recordReq);

    //提现
    WithdrawResult withdraw(WithDrawReq withDrawReq);

    //绑卡
    boolean cardBinding(CardBindReq cardBindReq);

    //查询银行卡信息
    List<CardBindResult> obtainCardBinds(Long uid);

}
