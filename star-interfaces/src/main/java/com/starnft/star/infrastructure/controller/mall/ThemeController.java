package com.starnft.star.infrastructure.controller.mall;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestPage;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.service.ThemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "主题相关接口「ThemeController」")
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

    @PostMapping("/seriesTheme/{id}")
    @ApiOperation("依据系列ID查找主题")
    public RopResponse seriesTheme(@PathVariable @ApiParam("系列id") Long  id, @RequestBody RequestPage requestPage){
        return  RopResponse.success(
                themeService
                        .queryMainThemeInfo(ThemeReq.builder()
                                .page(requestPage.getPage())
                                .size(requestPage.getSize())
                                .seriesId(id)
                                .build())
        );
    }

    @PostMapping("/theme/detail/{id}")
    @ApiOperation("主题详情")
    public RopResponse seriesTheme(@PathVariable @ApiParam("主题id") Long  id){
        return  RopResponse.success( themeService.queryThemeDetail(id) );
    }
}
