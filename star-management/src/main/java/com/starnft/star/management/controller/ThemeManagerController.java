package com.starnft.star.management.controller;

import com.github.pagehelper.PageInfo;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.infrastructure.entity.series.StarNftSeries;
import com.starnft.star.management.service.SeriesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("manager/series")
public class ThemeManagerController {

    @Resource
    private SeriesService seriesService;
    @PostMapping("/queryManagePageSeries")
    public RopResponse<PageInfo<StarNftSeries>> queryManagePageSeries(@RequestBody RequestConditionPage<StarNftSeries> requestConditionPage) {
        return   RopResponse.success(seriesService.querySeries(requestConditionPage));
    }
    @PostMapping("/insertManagePageSeries")
    public RopResponse<Boolean> insertManagePageSeries(@RequestBody StarNftSeries series) {
        return   RopResponse.success(seriesService.insertSeries(series));
    }
    @PostMapping("/updateManagePageSeries")
    public RopResponse<Boolean> updateManagePageSeries(@RequestBody StarNftSeries series) {
        return   RopResponse.success(seriesService.updateSeries(series));
    }
    @PostMapping("/deleteManagePageSeries/{id}")
    public RopResponse<Boolean> deleteManagePageSeries(@PathVariable Long id) {
        return   RopResponse.success(seriesService.deleteSeries(id));
    }
    @PostMapping("/detailManagePageSeries/{id}")
    public RopResponse<StarNftSeries> detailManagePageSeries(@PathVariable Long id) {
        return   RopResponse.success(seriesService.detailSeries(id));
    }
}
