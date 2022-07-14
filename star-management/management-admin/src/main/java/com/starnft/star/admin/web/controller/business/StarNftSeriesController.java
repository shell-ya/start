package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftSeries;
import com.starnft.star.business.service.IStarNftSeriesService;
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
 * 系列Controller
 *
 * @author shellya
 * @date 2022-05-27
 */
@RestController
@RequestMapping("/business/series")
public class StarNftSeriesController extends BaseController
{
    @Resource
    private IStarNftSeriesService starNftSeriesService;

    /**
     * 查询系列列表
     */
    @PreAuthorize("@ss.hasPermi('business:series:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftSeries starNftSeries)
    {
        startPage();
        List<StarNftSeries> list = starNftSeriesService.selectStarNftSeriesList(starNftSeries);
        return getDataTable(list);
    }

    /**
     * 导出系列列表
     */
    @PreAuthorize("@ss.hasPermi('business:series:export')")
    @Log(title = "系列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftSeries starNftSeries)
    {
        List<StarNftSeries> list = starNftSeriesService.selectStarNftSeriesList(starNftSeries);
        ExcelUtil<StarNftSeries> util = new ExcelUtil<StarNftSeries>(StarNftSeries.class);
        util.exportExcel(response, list, "系列数据");
    }

    /**
     * 获取系列详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:series:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftSeriesService.selectStarNftSeriesById(id));
    }

    /**
     * 新增系列
     */
    @PreAuthorize("@ss.hasPermi('business:series:add')")
    @Log(title = "系列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftSeries starNftSeries)
    {
        return toAjax(starNftSeriesService.insertStarNftSeries(starNftSeries,getUserId()));
    }

    /**
     * 修改系列
     */
    @PreAuthorize("@ss.hasPermi('business:series:edit')")
    @Log(title = "系列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftSeries starNftSeries)
    {
        return toAjax(starNftSeriesService.updateStarNftSeries(starNftSeries));
    }

    /**
     * 删除系列
     */
    @PreAuthorize("@ss.hasPermi('business:series:remove')")
    @Log(title = "系列", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftSeriesService.deleteStarNftSeriesByIds(ids));
    }
}
