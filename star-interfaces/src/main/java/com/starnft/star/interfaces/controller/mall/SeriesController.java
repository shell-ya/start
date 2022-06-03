package com.starnft.star.interfaces.controller.mall;

import com.starnft.star.common.RopResponse;
import com.starnft.star.domain.series.model.vo.SeriesVO;
import com.starnft.star.domain.series.service.SeriesService;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Harlan
 * @date 2022/06/02 10:25
 */
@RestController
@Api(tags = "系列相关接口「SeriesController」")
@RequestMapping("/series")
@RequiredArgsConstructor
public class SeriesController {

    private final SeriesService seriesService;

    @GetMapping("/")
    @ApiOperation("获取所有系列")
    @TokenIgnore
    public RopResponse<List<SeriesVO>> getAllSeries(@ApiParam("系列类型 1：藏品 2：盲盒 默认1") @RequestParam(required = false, defaultValue = "1") Integer type) {
        return RopResponse.success(this.seriesService.querySeriesByType(type));
    }
}
