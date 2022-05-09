package com.starnft.star.infrastructure.controller.mall;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestPage;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.service.ThemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "系列相关接口「SeriesController」")
@RequestMapping("/theme")
public class ThemeController {
    @Resource
    ThemeService themeService;
    @PostMapping("/main")
    @ApiOperation("首页系列推荐接口")
    public RopResponse mainTheme(@RequestBody RequestPage requestPage){
        return  RopResponse.success(
                themeService
                        .queryMainThemeInfo(ThemeReq.builder()
                                .page(requestPage.getPage())
                                .size(requestPage.getSize())
                                .isRecommend(Boolean.TRUE)
                                .build())
        );
    }
}
