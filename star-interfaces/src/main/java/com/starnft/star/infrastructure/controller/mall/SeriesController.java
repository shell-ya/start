package com.starnft.star.infrastructure.controller.mall;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestPage;
import com.starnft.star.domain.series.model.req.SeriesReq;
import com.starnft.star.domain.series.service.SeriesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/series")
public class SeriesController {
    @Resource
    SeriesService seriesService;
    //主页显示系列
    @PostMapping("/main")
    public RopResponse mainSeries(@RequestBody  RequestPage requestPage){
      return  RopResponse.success(seriesService.queryMainSeriesInfo((SeriesReq) requestPage));
    }
}
