package com.starnft.star.interfaces.controller.compose;

import com.starnft.star.application.process.compose.IComposeCore;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.domain.compose.model.req.ComposeReq;
import com.starnft.star.domain.compose.service.IComposeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "合成接口「ComposeController」")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/compose")
public class ComposeController {
    final IComposeService composeService;
    final IComposeCore composeCore;
    @PostMapping("list")
    public RopResponse list(RequestConditionPage<ComposeReq> reqRequestConditionPage) {
        return RopResponse.success(composeService.composeList(reqRequestConditionPage));
    }

    @PostMapping("details/{id}")
    public RopResponse details(@PathVariable Long id) {
        return RopResponse.success(composeService.composeDetails(id));
    }
    @PostMapping("details/material")
    public RopResponse material(@PathVariable Long id) {
        return RopResponse.success(composeCore.composeMaterial(id));
    }
    @PostMapping("details/synthetic")
    public RopResponse synthetic(@PathVariable Long id) {
        return RopResponse.success(composeService.composeDetails(id));
    }
}
