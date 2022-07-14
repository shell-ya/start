package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarRankConfig;
import com.starnft.star.business.service.IStarRankConfigService;
import com.starnft.star.common.annotation.Log;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.core.page.TableDataInfo;
import com.starnft.star.common.enums.BusinessType;
import com.starnft.star.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 排行榜Controller
 *
 * @author shellya
 * @date 2022-06-28
 */
@RestController
@RequestMapping("/business/rankconfig")
public class StarRankConfigController extends BaseController
{
    @Resource
    private IStarRankConfigService starRankConfigService;

    /**
     * 查询排行榜列表
     */
    @PreAuthorize("@ss.hasPermi('business:rankconfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarRankConfig starRankConfig)
    {
        startPage();
        List<StarRankConfig> list = starRankConfigService.selectStarRankConfigList(starRankConfig);
        return getDataTable(list);
    }

    /**
     * 导出排行榜列表
     */
    @PreAuthorize("@ss.hasPermi('business:rankconfig:export')")
    @Log(title = "排行榜", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarRankConfig starRankConfig)
    {
        List<StarRankConfig> list = starRankConfigService.selectStarRankConfigList(starRankConfig);
        ExcelUtil<StarRankConfig> util = new ExcelUtil<StarRankConfig>(StarRankConfig.class);
        util.exportExcel(response, list, "排行榜数据");
    }

    /**
     * 获取排行榜详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:rankconfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starRankConfigService.selectStarRankConfigById(id));
    }

    /**
     * 新增排行榜
     */
    @PreAuthorize("@ss.hasPermi('business:rankconfig:add')")
    @Log(title = "排行榜", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarRankConfig starRankConfig)
    {
        return toAjax(starRankConfigService.insertStarRankConfig(starRankConfig));
    }

    /**
     * 修改排行榜
     */
    @PreAuthorize("@ss.hasPermi('business:rankconfig:edit')")
    @Log(title = "排行榜", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarRankConfig starRankConfig)
    {
        return toAjax(starRankConfigService.updateStarRankConfig(starRankConfig));
    }

    /**
     * 删除排行榜
     */
    @PreAuthorize("@ss.hasPermi('business:rankconfig:remove')")
    @Log(title = "排行榜", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starRankConfigService.deleteStarRankConfigByIds(ids));
    }
}
