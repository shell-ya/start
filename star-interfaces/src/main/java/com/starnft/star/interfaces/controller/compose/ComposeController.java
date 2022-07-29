package com.starnft.star.interfaces.controller.compose;

import com.starnft.star.application.process.compose.IComposeCore;
import com.starnft.star.application.process.compose.model.req.UserMaterialReq;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.domain.compose.model.req.ComposeManageReq;
import com.starnft.star.domain.compose.model.req.ComposeReq;
import com.starnft.star.domain.compose.service.IComposeService;
import com.starnft.star.interfaces.interceptor.UserResolverInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "合成接口「ComposeController」")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/compose")
public class ComposeController {
    final IComposeService composeService;
    final IComposeCore composeCore;

    @ApiOperation("合成列表")
    @PostMapping("list")
    public RopResponse list(@RequestBody  RequestConditionPage<ComposeReq> reqRequestConditionPage) {
        return RopResponse.success(composeService.composeList(reqRequestConditionPage));
    }

    @ApiOperation("合成详情")
    @PostMapping("details/{id}")
    public RopResponse details(@PathVariable Long id) {
        return RopResponse.success(composeCore.composeDetails(id));
    }

    @ApiOperation("用户素材列表获取")
    @PostMapping("/user/material/{id}")
    public RopResponse material( UserResolverInfo userResolverInfo, @PathVariable("id") Long id) {
        return RopResponse.success(composeCore.composeUserMaterial(new UserMaterialReq(id, userResolverInfo.getUserId())));
    }
    @ApiOperation("合成操作")
    @PostMapping("/manage")
    public RopResponse manage(@RequestBody ComposeManageReq composeManageReq) {
        return RopResponse.success(composeCore.composeManage(composeManageReq));
    }

}
