package com.starnft.star.admin.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.starnft.star.business.domain.DrawActivity;
import com.starnft.star.business.service.IDrawActivityService;
import com.starnft.star.common.annotation.Log;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.core.page.TableDataInfo;
import com.starnft.star.common.enums.BusinessType;
import com.starnft.star.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 活动配置Controller
 *
 * @author zz
 * @date 2022-09-28
 */
@RestController
@RequestMapping("/business/drawactivity")
public class DrawActivityController extends BaseController
{
    @Autowired
    private IDrawActivityService drawActivityService;

    /**
     * 查询活动配置列表
     */
    @PreAuthorize("@ss.hasPermi('business:drawactivity:list')")
    @GetMapping("/list")
    public TableDataInfo list(DrawActivity drawActivity)
    {
        startPage();
        List<DrawActivity> list = drawActivityService.selectDrawActivityList(drawActivity);
        return getDataTable(list);
    }

    /**
     * 导出活动配置列表
     */
    @PreAuthorize("@ss.hasPermi('business:drawactivity:export')")
    @Log(title = "活动配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DrawActivity drawActivity)
    {
        List<DrawActivity> list = drawActivityService.selectDrawActivityList(drawActivity);
        ExcelUtil<DrawActivity> util = new ExcelUtil<DrawActivity>(DrawActivity.class);
        util.exportExcel(response, list, "活动配置数据");
    }

    /**
     * 获取活动配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:drawactivity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(drawActivityService.selectDrawActivityById(id));
    }

    /**
     * 新增活动配置
     */
    @PreAuthorize("@ss.hasPermi('business:drawactivity:add')")
    @Log(title = "活动配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DrawActivity drawActivity)
    {
        return toAjax(drawActivityService.insertDrawActivity(drawActivity));
    }

    /**
     * 修改活动配置
     */
    @PreAuthorize("@ss.hasPermi('business:drawactivity:edit')")
    @Log(title = "活动配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DrawActivity drawActivity)
    {
        return toAjax(drawActivityService.updateDrawActivity(drawActivity));
    }

    /**
     * 删除活动配置
     */
    @PreAuthorize("@ss.hasPermi('business:drawactivity:remove')")
    @Log(title = "活动配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(drawActivityService.deleteDrawActivityByIds(ids));
    }
}
