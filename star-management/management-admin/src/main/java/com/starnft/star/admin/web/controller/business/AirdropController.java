package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftThemeInfo;
import com.starnft.star.business.domain.StarNftThemeNumber;
import com.starnft.star.business.service.IStarNftThemeInfoService;
import com.starnft.star.business.service.IStarNftThemeNumberService;
import com.starnft.star.common.core.domain.AjaxResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/business/airdrop")
public class AirdropController {
    @Resource
 private IStarNftThemeNumberService iStarNftThemeNumberService;

    @Resource
    private IStarNftThemeInfoService iStarNftThemeInfoService;
    /**
     * 查询编号
     */
    @PreAuthorize("@ss.hasPermi('business:airdrop:listNumber')")
    @PostMapping(value = "/listNumber")
    public AjaxResult listNumber(@RequestBody  StarNftThemeNumber nftThemeNumber)
    {
        return AjaxResult.success(iStarNftThemeNumberService.selectStarNftThemeNumberList(nftThemeNumber));
    }
    /**
     * 查询所有主题
     */
    @PreAuthorize("@ss.hasPermi('business:airdrop:listTheme')")
    @PostMapping(value = "/listTheme")
    public AjaxResult listTheme(@RequestBody StarNftThemeInfo starNftThemeInfo)
    {
        return AjaxResult.success(iStarNftThemeInfoService.selectStarNftThemeInfoList(starNftThemeInfo));
    }
}
