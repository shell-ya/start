package com.starnft.star.interfaces.controller.pay;

import com.starnft.star.application.process.wallet.IWalletCore;
import com.starnft.star.application.process.wallet.req.PayRecordReq;
import com.starnft.star.application.process.wallet.req.RechargeFacadeReq;
import com.starnft.star.application.process.wallet.res.RechargeReqResult;
import com.starnft.star.application.process.wallet.res.TransactionRecord;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.wallet.model.req.CardBindReq;
import com.starnft.star.domain.wallet.model.req.WithDrawReq;
import com.starnft.star.domain.wallet.model.req.WithdrawCancelReq;
import com.starnft.star.domain.wallet.model.res.CardBindResult;
import com.starnft.star.domain.wallet.model.res.WithdrawResult;
import com.starnft.star.domain.wallet.model.vo.BankRelationVO;
import com.starnft.star.domain.wallet.service.WalletService;
import com.starnft.star.interfaces.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "钱包接口「WalletController」")
@RequestMapping(value = "/wallet")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WalletController {
    private final IWalletCore walletCore;
    private final WalletService walletService;

    @ApiOperation("充值")
    @PostMapping("/recharge")
    public RopResponse<RechargeReqResult> recharge(@Validated @RequestBody RechargeFacadeReq req) {
        req.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(walletCore.recharge(req));
    }


    @ApiOperation("交易记录")
    @PostMapping("/transactions")
    public RopResponse<ResponsePageResult<TransactionRecord>> transactions(@Validated @RequestBody PayRecordReq req) {
        req.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(walletCore.walletRecordQuery(req));
    }


    @ApiOperation("提现申请")
    @PostMapping("/withdraw")
    public RopResponse<WithdrawResult> withdraw(@Validated @RequestBody WithDrawReq req) {
        req.setUid(UserContext.getUserId().getUserId());
        return RopResponse.success(walletCore.withdraw(req));
    }

    @ApiOperation("提现申请")
    @PostMapping("/withdraw/cancel")
    public RopResponse<WithdrawResult> withdrawCancel(@Validated @RequestBody WithdrawCancelReq req) {
        req.setUid(UserContext.getUserId().getUserId());
        return RopResponse.success(walletService.withdrawCancel(req));
    }

    @ApiOperation("银行卡绑定")
    @PostMapping("/cardbinding")
    public RopResponse<?> cardBinding(@Validated @RequestBody CardBindReq cardBindReq) {
        cardBindReq.setUid(UserContext.getUserId().getUserId());
        boolean isSuccess = walletCore.cardBinding(cardBindReq);
        if (isSuccess) {
            return RopResponse.successNoData();
        }
        return RopResponse.fail(StarError.CARD_BINDING_FAILED);
    }

    @ApiOperation("已绑定银行卡查询")
    @PostMapping("/bindingslist")
    public RopResponse<List<CardBindResult>> bindingsList() {
        return RopResponse.success(walletCore.obtainCardBinds(UserContext.getUserId().getUserId()));
    }

    @ApiOperation("删除银行卡")
    @PostMapping("/deletecards")
    public RopResponse<?> deletecards(@Validated @RequestBody List<@Valid BankRelationVO> bankRelations) {
        for (BankRelationVO bankRelation : bankRelations) {
            bankRelation.setUid(UserContext.getUserId().getUserId());
        }
        boolean isSuccess = walletService.deleteCards(bankRelations);
        if (isSuccess) {
            return RopResponse.successNoData();
        }
        return RopResponse.fail(StarError.SYSTEM_ERROR);
    }

    @ApiOperation("默认银行卡设置")
    @PostMapping("/defaultcardsetting")
    public RopResponse<?> setDefaultCard(@Validated @RequestBody BankRelationVO relationVO) {
        relationVO.setUid(UserContext.getUserId().getUserId());
        if (walletService.setDefaultCard(relationVO)) {
            return RopResponse.successNoData();
        }
        return RopResponse.fail(StarError.SYSTEM_ERROR, "可能您未绑定该卡");
    }


}
