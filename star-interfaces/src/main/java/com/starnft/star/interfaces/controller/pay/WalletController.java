package com.starnft.star.interfaces.controller.pay;

import com.starnft.star.application.process.user.IWalletCore;
import com.starnft.star.application.process.user.req.PayRecordReq;
import com.starnft.star.common.RopResponse;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
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
    @TokenIgnore
    public RopResponse transactions(@Validated @RequestBody PayRecordReq req) {
        return RopResponse.success(walletCore.walletRecordQuery(req));
    }


}
