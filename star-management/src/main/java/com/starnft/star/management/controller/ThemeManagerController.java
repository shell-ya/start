package com.starnft.star.management.controller;

import com.github.pagehelper.PageInfo;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.infrastructure.entity.theme.StarNftThemeInfo;
import com.starnft.star.management.service.ManagerThemeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("manager/series")
public class ThemeManagerController {
@Resource
  private  ManagerThemeService managerThemeService;
    @PostMapping("/queryManagePageSeries")
    public RopResponse<PageInfo<StarNftThemeInfo>> queryManagePageSeries(@RequestBody RequestConditionPage<StarNftThemeInfo> requestConditionPage) {
        return   RopResponse.success(managerThemeService.queryTheme(requestConditionPage));
    }
    @PostMapping("/insertManagePageSeries")
    public RopResponse<Boolean> insertManagePageSeries(@RequestBody StarNftThemeInfo series) {
        return   RopResponse.success(managerThemeService.insertTheme(series));
    }
    @PostMapping("/updateManagePageSeries")
    public RopResponse<Boolean> updateManagePageSeries(@RequestBody StarNftThemeInfo series) {
        return   RopResponse.success(managerThemeService.updateTheme(series));
    }
    @PostMapping("/deleteManagePageSeries/{id}")
    public RopResponse<Boolean> deleteManagePageSeries(@PathVariable Long id) {
        return   RopResponse.success(managerThemeService.deleteTheme(id));
    }
    @PostMapping("/detailManagePageSeries/{id}")
    public RopResponse<StarNftThemeInfo> detailManagePageSeries(@PathVariable Long id) {
        return   RopResponse.success(managerThemeService.detailTheme(id));
    }
}
