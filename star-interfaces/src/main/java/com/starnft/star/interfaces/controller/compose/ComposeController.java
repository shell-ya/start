package com.starnft.star.interfaces.controller.compose;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.domain.compose.model.req.ComposeReq;
import com.starnft.star.domain.compose.service.IComposeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("list")
    public RopResponse list(RequestConditionPage<ComposeReq> reqRequestConditionPage){
       return RopResponse.success(composeService.composeList(reqRequestConditionPage));
    }
}
