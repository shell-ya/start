package com.starnft.star.management.controller;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.banner.model.dto.BannerConditionDto;
import com.starnft.star.domain.banner.model.dto.BannerDto;
import com.starnft.star.management.service.ManagerBannerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Date 2022/5/9 8:36 PM
 * @Author ： shellya
 */
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Resource
    private ManagerBannerService bannerService;

    @PostMapping("/save")
    public RopResponse save(@RequestBody BannerDto dto){
        return  RopResponse.success(bannerService.saveBanner(dto)) ;
    }

    @DeleteMapping("/delete/{id}")
    public RopResponse delete(@PathVariable("id") Long id) {
        return   RopResponse.success(bannerService.deleteBanner(id)) ;
    }

    @PutMapping("/modify")
    public RopResponse modify(@RequestBody BannerDto dto){
        return  RopResponse.success(bannerService.modifyBanner(dto));
    }

    @PostMapping("/page")
    public ResponsePageResult<Object> page(@RequestBody RequestConditionPage<BannerConditionDto> reqPage){
        return bannerService.getBannerPage(reqPage);
    }
}
