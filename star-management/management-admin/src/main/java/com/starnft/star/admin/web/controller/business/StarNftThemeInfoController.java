package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftThemeInfo;
import com.starnft.star.business.domain.vo.StarNftThemeInfoVo;
import com.starnft.star.business.service.IStarNftThemeInfoService;
import com.starnft.star.common.annotation.Log;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.core.page.TableDataInfo;
import com.starnft.star.common.enums.BusinessType;
import com.starnft.star.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 主题Controller
 *
 * @author shellya
 * @date 2022-06-03
 */
@RestController
@RequestMapping("/business/info")
public class StarNftThemeInfoController extends BaseController
{
    @Resource
    private IStarNftThemeInfoService starNftThemeInfoService;

    /**
     * 查询主题列表
     */
    @PreAuthorize("@ss.hasPermi('business:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftThemeInfo starNftThemeInfo)
    {
        startPage();
        List<StarNftThemeInfoVo> list = starNftThemeInfoService.selectStarNftThemeInfoVoList(starNftThemeInfo);
        return getDataTable(list);
    }

    /**
     * 导出主题列表
     */
    @PreAuthorize("@ss.hasPermi('business:info:export')")
    @Log(title = "主题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftThemeInfo starNftThemeInfo)
    {
        List<StarNftThemeInfo> list = starNftThemeInfoService.selectStarNftThemeInfoList(starNftThemeInfo);
        ExcelUtil<StarNftThemeInfo> util = new ExcelUtil<StarNftThemeInfo>(StarNftThemeInfo.class);
        util.exportExcel(response, list, "主题数据");
    }

    /**
     * 获取主题详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftThemeInfoService.selectStarNftThemeInfoById(id));
    }

    /**
     * 新增主题
     */
    @PreAuthorize("@ss.hasPermi('business:info:add')")
    @Log(title = "主题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftThemeInfo starNftThemeInfo)
    {
        return toAjax(starNftThemeInfoService.insertStarNftThemeInfo(starNftThemeInfo,getUserId()));
    }

    /**
     * 修改主题
     */
    @PreAuthorize("@ss.hasPermi('business:info:edit')")
    @Log(title = "主题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftThemeInfo starNftThemeInfo)
    {
        return toAjax(starNftThemeInfoService.updateStarNftThemeInfo(starNftThemeInfo));
    }

    /**
     * 删除主题
     */
    @PreAuthorize("@ss.hasPermi('business:info:remove')")
    @Log(title = "主题", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftThemeInfoService.deleteStarNftThemeInfoByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('business:info:select')")
    @GetMapping("/select/{publisherId}")
    public AjaxResult select(@PathVariable(value = "publisherId") Long publisherId){
        return AjaxResult.success(starNftThemeInfoService.selectThemeInfoByPublisherId(publisherId));
    }

    @PreAuthorize("@ss.hasPermi('business:info:select')")
    @PostMapping("/theme/publisher")
    public AjaxResult publisher(@RequestBody StarNftThemeInfo starNftThemeInfo){
        return AjaxResult.success(starNftThemeInfoService.publishTheme(starNftThemeInfo,getUserId()));
    }
}
