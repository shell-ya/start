package com.starnft.star.domain.wallet.service;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.wallet.model.req.*;
import com.starnft.star.domain.wallet.model.res.*;
import com.starnft.star.domain.wallet.model.vo.BankRelationVO;
import com.starnft.star.domain.wallet.model.vo.RechargeVO;
import com.starnft.star.domain.wallet.model.vo.WalletRecordVO;
import com.starnft.star.domain.wallet.model.vo.WithdrawRecordVO;

import java.util.List;

public interface WalletService {

    //渠道参数验证
    void verifyParam(String channel);

    //钱包余额查询
    WalletResult queryWalletInfo(WalletInfoReq walletInfoReq);

    //生成预充值记录
    boolean rechargeRecordGenerate(WalletRecordReq walletRecordReq);

    //根据单号查询记录
    WalletRecordVO queryWalletRecordByOrderNo(String orderNo);

    //提现金额计算
    CalculateResult withdrawMoneyCalculate(CalculateReq calculate);

    //提现
    WithdrawResult withdraw(WithDrawReq withDrawReq);

    //取消提现
    WithdrawResult withdrawCancel(WithdrawCancelReq cancelReq);

    //查询交易记录
    ResponsePageResult<WalletRecordVO> queryTransactionRecord(TransactionRecordQueryReq queryReq);

    //支付成功 回调修改钱包余额 变动以及状态
    boolean rechargeProcess(RechargeVO rechargeVO);

    //交易结果缓存查询 轮训5次 间隔三秒
    TxResultRes txResultCacheQuery(TxResultReq txResultReq);

    //交易结果db查询 缓存轮训后仍无结果查该接口
    TxResultRes txResultQuery(TxResultReq txResultReq);

    //银行卡绑定
    boolean cardBind(CardBindReq cardBindReq);

    //用户绑定银行卡查询
    List<CardBindResult> obtainCards(Long uid);

    //删除银行卡
    boolean deleteCards(List<BankRelationVO> bankRelations);

    //设置银行卡为默认卡
    boolean setDefaultCard(BankRelationVO relationVO);

    //单号查询提现记录
    WithdrawRecordVO queryWithDrawByTradeNo(String recordSn);
}
