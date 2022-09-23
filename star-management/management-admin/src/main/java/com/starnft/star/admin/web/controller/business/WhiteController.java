package com.starnft.star.admin.web.controller.business;


import com.starnft.star.business.domain.WhiteListConfig;
import com.starnft.star.business.domain.WhiteListDetail;
import com.starnft.star.business.domain.vo.StarScheduleSeckillVo;
import com.starnft.star.business.domain.vo.WhiteDetailVo;
import com.starnft.star.business.service.IWhiteService;
import com.starnft.star.common.annotation.Log;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.core.page.TableDataInfo;
import com.starnft.star.common.enums.BusinessType;
import com.starnft.star.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/business/white")
public class WhiteController extends BaseController {

    @Resource
    private IWhiteService whiteService;

    @PreAuthorize("@ss.hasPermi('business:white:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody  WhiteListConfig config)
    {
        startPage();
        List<WhiteListConfig> list = whiteService.queryWhiteConfigList(config);
        return getDataTable(list);
    }

    @Log(title = "名单导入", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('business:white:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, Long whiteId) throws Exception
    {
        ExcelUtil<WhiteDetailVo> util = new ExcelUtil<WhiteDetailVo>(WhiteDetailVo.class);
        List<WhiteDetailVo> whiteDetailVos = util.importExcel(file.getInputStream());
        String message = whiteService.importWhiteDetail(whiteDetailVos, whiteId);
        return AjaxResult.success(message);
    }


    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<WhiteDetailVo> util = new ExcelUtil<>(WhiteDetailVo.class);
        util.importTemplateExcel(response, "白名单模版");
    }
    @PreAuthorize("@ss.hasPermi('business:white:list')")
    @PostMapping("/getOneConfig")
    public AjaxResult getOneConfig(@RequestBody Long whiteId){
        return AjaxResult.success(whiteService.getOneConfig(whiteId));
    }

    @PreAuthorize("@ss.hasPermi('business:white:list')")
    @GetMapping("/whiteuse")
    public TableDataInfo whiteDetail(WhiteListDetail whiteListDetail){
        startPage();
        List<WhiteListDetail> whiteListDetails = whiteService.queryWhiteList(whiteListDetail);
        return getDataTable(whiteListDetails);
    }

    @PreAuthorize("@ss.hasPermi('business:white:list')")
    @PostMapping("/addWhite")
    public AjaxResult addWhite(@RequestBody WhiteListConfig whiteListConfig){
        return AjaxResult.success(whiteService.insertWhiteConfig(whiteListConfig));
    }

}
