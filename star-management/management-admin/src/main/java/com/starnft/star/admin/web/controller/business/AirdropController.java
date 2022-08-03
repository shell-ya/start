package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.AirdropThemeRecord;
import com.starnft.star.business.domain.StarNftThemeInfo;
import com.starnft.star.business.domain.StarNftThemeNumber;
import com.starnft.star.business.domain.dto.AirdropRecordDto;
import com.starnft.star.business.service.IAirdropThemeRecordService;
import com.starnft.star.business.service.IStarNftThemeInfoService;
import com.starnft.star.business.service.IStarNftThemeNumberService;
import com.starnft.star.business.service.impl.AirdropThemeRecordServiceImpl;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.core.page.TableDataInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/business/airdrop")
public class AirdropController extends BaseController {
    @Resource
 private IStarNftThemeNumberService iStarNftThemeNumberService;

    @Resource
    private IStarNftThemeInfoService iStarNftThemeInfoService;

    @Resource
    private IAirdropThemeRecordService airdropThemeRecordService;
    /**
     * 查询编号
     */
    @PreAuthorize("@ss.hasPermi('business:airdrop:listNumber')")
    @PostMapping(value = "/listNumber")
    public TableDataInfo listNumber(@RequestBody  StarNftThemeNumber nftThemeNumber)
    {
        startPage();
        List<StarNftThemeNumber> starNftThemeNumbers = iStarNftThemeNumberService.selectStarNftThemeNumberList(nftThemeNumber);
        return getDataTable(starNftThemeNumbers);
    }
    /**
     * 查询所有主题
     */
    @PreAuthorize("@ss.hasPermi('business:airdrop:listTheme')")
    @PostMapping(value = "/listTheme")
    public TableDataInfo listTheme(@RequestBody StarNftThemeInfo starNftThemeInfo)
    {
        startPage();
        List<StarNftThemeInfo> starNftThemeInfos = iStarNftThemeInfoService.selectStarNftThemeInfoList(starNftThemeInfo);
        return getDataTable(starNftThemeInfos);
    }

//    @PreAuthorize("@ss.hasPermi('business:airdrop:add')")
    @PostMapping(value = "/addAirdrop")
    public AjaxResult addAirdrop(@RequestBody AirdropThemeRecord record)
    {
        return AjaxResult.success(airdropThemeRecordService.addUserAirdrop(record));
    }

    @PostMapping(value = "/airdropList")
    public AjaxResult airdropList(@RequestBody List<AirdropRecordDto> records)
    {
        return AjaxResult.success(airdropThemeRecordService.addUserAirdropList(records));
    }

    @PostMapping(value = "/zhuayuliu")
    public AjaxResult zhuyuliu(@RequestBody AirdropRecordDto dto){
        return AjaxResult.success(airdropThemeRecordService.zhuyuliu(dto));
    }

    @PostMapping(value = "/randomDrop")
    public AjaxResult randomDrop(@RequestBody List<AirdropRecordDto> dtoList){
        return AjaxResult.success(airdropThemeRecordService.airdropProcess(dtoList));
    }

}
