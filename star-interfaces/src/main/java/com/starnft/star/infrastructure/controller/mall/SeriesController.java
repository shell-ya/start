package com.starnft.star.infrastructure.controller.mall;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestPage;
import com.starnft.star.domain.series.model.req.SeriesReq;
import com.starnft.star.domain.series.service.SeriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "系列相关接口「SeriesController」")
@RequestMapping("/series")
public class SeriesController {
    @Resource
    SeriesService seriesService;
    //主页显示系列
    @PostMapping("/main")
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
