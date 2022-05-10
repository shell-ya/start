package com.starnft.star.management.controller;

import com.github.pagehelper.PageInfo;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.infrastructure.entity.series.StarNftSeries;
import com.starnft.star.management.service.ManagerSeriesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("manager/series")
public class SeriesManagerController {
    @Resource
    private ManagerSeriesService managerSeriesService;
    @PostMapping("/queryManagePageSeries")
    public RopResponse<PageInfo<StarNftSeries>> queryManagePageSeries(@RequestBody RequestConditionPage<StarNftSeries> requestConditionPage) {
         return   RopResponse.success(managerSeriesService.querySeries(requestConditionPage));
    }
    @PostMapping("/insertManageSeries")
    public RopResponse<Boolean> insertManagePageSeries(@RequestBody StarNftSeries series) {
        return   RopResponse.success(managerSeriesService.insertSeries(series));
    }
    @PostMapping("/updateManageSeries")
    public RopResponse<Boolean> updateManagePageSeries(@RequestBody StarNftSeries series) {
        return   RopResponse.success(managerSeriesService.updateSeries(series));
    }
    @PostMapping("/deleteManageSeries/{id}")
    public RopResponse<Boolean> deleteManagePageSeries(@PathVariable Long id) {
        return   RopResponse.success(managerSeriesService.deleteSeries(id));
    }
    @PostMapping("/detailManageSeries/{id}")
    public RopResponse<StarNftSeries> detailManagePageSeries(@PathVariable Long id) {
        return   RopResponse.success(managerSeriesService.detailSeries(id));
    }
}
