package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarBanner;
import com.starnft.star.business.service.IStarBannerService;
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
 * 轮播图Controller
 *
 * @author shellya
 * @date 2022-06-10
 */
@RestController
@RequestMapping("/business/banner")
public class StarBannerController extends BaseController
{
    @Resource
    private IStarBannerService starBannerService;

    /**
     * 查询轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('business:banner:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarBanner starBanner)
    {
        startPage();
        List<StarBanner> list = starBannerService.selectStarBannerList(starBanner);
        return getDataTable(list);
    }

    /**
     * 导出轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('business:banner:export')")
    @Log(title = "轮播图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarBanner starBanner)
    {
        List<StarBanner> list = starBannerService.selectStarBannerList(starBanner);
        ExcelUtil<StarBanner> util = new ExcelUtil<StarBanner>(StarBanner.class);
        util.exportExcel(response, list, "轮播图数据");
    }

    /**
     * 获取轮播图详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:banner:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starBannerService.selectStarBannerById(id));
    }

    /**
     * 新增轮播图
     */
    @PreAuthorize("@ss.hasPermi('business:banner:add')")
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarBanner starBanner)
    {
        return toAjax(starBannerService.insertStarBanner(starBanner));
    }

    /**
     * 修改轮播图
     */
    @PreAuthorize("@ss.hasPermi('business:banner:edit')")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarBanner starBanner)
    {
        return toAjax(starBannerService.updateStarBanner(starBanner));
    }

    /**
     * 删除轮播图
     */
    @PreAuthorize("@ss.hasPermi('business:banner:remove')")
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starBannerService.deleteStarBannerByIds(ids));
    }
}
