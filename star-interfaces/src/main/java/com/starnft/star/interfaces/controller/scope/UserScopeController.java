package com.starnft.star.interfaces.controller.scope;

import com.starnft.star.application.process.scope.IScopeCore;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "钱包接口「WalletController」")
@RequestMapping(value = "/wallet")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserScopeController {
    private final IScopeCore iScopeCore;
//    private final
}
