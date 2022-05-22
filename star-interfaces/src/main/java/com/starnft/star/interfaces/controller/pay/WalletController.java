package com.starnft.star.interfaces.controller.pay;

import com.starnft.star.application.process.wallet.IWalletCore;
import com.starnft.star.application.process.wallet.req.PayRecordReq;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.domain.wallet.model.req.CardBindReq;
import com.starnft.star.domain.wallet.model.req.WithDrawReq;
import com.starnft.star.interfaces.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "钱包接口「WalletController」")
@RequestMapping(value = "/wallet")

public class WalletController {
    @Resource
    private IWalletCore walletCore;

    @ApiOperation("交易记录")
    @PostMapping("/transactions")
    public RopResponse transactions(@Validated @RequestBody PayRecordReq req) {
        req.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(walletCore.walletRecordQuery(req));
    }


    @ApiOperation("提现申请")
    @PostMapping("/withdraw")
    public RopResponse withdraw(@Validated @RequestBody WithDrawReq req) {
        req.setUid(UserContext.getUserId().getUserId());
        return RopResponse.success(walletCore.withdraw(req));
    }

    @ApiOperation("银行卡绑定")
    @PostMapping("/cardbinding")
    public RopResponse withdraw(@Validated @RequestBody CardBindReq cardBindReq) {
        cardBindReq.setUid(UserContext.getUserId().getUserId());
        boolean isSuccess = walletCore.cardBinding(cardBindReq);
        if (isSuccess) {
            return RopResponse.successNoData();
        }
        return RopResponse.fail(StarError.CARD_BINDING_FAILED);
    }

    @ApiOperation("已绑定银行卡查询")
    @PostMapping("/bindingslist")
    public RopResponse withdraw() {
        return RopResponse.success(walletCore.obtainCardBinds(UserContext.getUserId().getUserId()));
    }


}
