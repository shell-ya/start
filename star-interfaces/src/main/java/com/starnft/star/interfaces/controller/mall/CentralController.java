package com.starnft.star.interfaces.controller.mall;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestPage;
import com.starnft.star.domain.series.model.req.SeriesReq;
import com.starnft.star.domain.series.service.SeriesService;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.service.ThemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "首页进入接口「CentralController」")
@RequestMapping("/central")
public class CentralController {
    @Resource
    SeriesService seriesService;

    @Resource
    ThemeService themeService;
    @PostMapping("/theme")
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
    //主页显示系列
    @PostMapping("/series")
    @ApiOperation("首页系列推荐接口")
    public RopResponse mainSeries(@RequestBody  RequestPage requestPage){
      return  RopResponse.success(
                seriesService
               .queryMainSeriesInfo(SeriesReq.builder()
                      .page(requestPage.getPage())
                      .size(requestPage.getSize())
                      .build())
      );
    }
}
