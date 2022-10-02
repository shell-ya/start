package com.starnft.star.admin.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.starnft.star.business.domain.vo.AwardVo;
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
import com.starnft.star.business.domain.DrawStrategyDetail;
import com.starnft.star.business.service.IDrawStrategyDetailService;


/**
 * 策略明细Controller
 *
 * @author zz
 * @date 2022-09-30
 */
@RestController
@RequestMapping("/drawstrategy/drawstrategy")
public class DrawStrategyDetailController extends BaseController
{
    @Autowired
    private IDrawStrategyDetailService drawStrategyDetailService;

    /**
     * 查询策略明细列表
     */
    @PreAuthorize("@ss.hasPermi('drawstrategy:drawstrategy:list')")
    @GetMapping("/list")
    public TableDataInfo list(DrawStrategyDetail drawStrategyDetail)
    {
        startPage();
        List<DrawStrategyDetail> list = drawStrategyDetailService.selectDrawStrategyDetailList(drawStrategyDetail);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('drawstrategy:drawstrategy:list')")
    @GetMapping("/award")
    public AjaxResult award(Long drawStrategyId)
    {
        List<AwardVo> list = drawStrategyDetailService.selectDrawStrategyDetailListByStrategyId(drawStrategyId);
        return AjaxResult.success(list);
    }

    /**
     * 导出策略明细列表
     */
    @PreAuthorize("@ss.hasPermi('drawstrategy:drawstrategy:export')")
    @Log(title = "策略明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DrawStrategyDetail drawStrategyDetail)
    {
        List<DrawStrategyDetail> list = drawStrategyDetailService.selectDrawStrategyDetailList(drawStrategyDetail);
        ExcelUtil<DrawStrategyDetail> util = new ExcelUtil<DrawStrategyDetail>(DrawStrategyDetail.class);
        util.exportExcel(response, list, "策略明细数据");
    }

    /**
     * 获取策略明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('drawstrategy:drawstrategy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(drawStrategyDetailService.selectDrawStrategyDetailById(id));
    }

    /**
     * 新增策略明细
     */
    @PreAuthorize("@ss.hasPermi('drawstrategy:drawstrategy:add')")
    @Log(title = "策略明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DrawStrategyDetail drawStrategyDetail)
    {
        return toAjax(drawStrategyDetailService.insertDrawStrategyDetail(drawStrategyDetail));
    }

    /**
     * 修改策略明细
     */
    @PreAuthorize("@ss.hasPermi('drawstrategy:drawstrategy:edit')")
    @Log(title = "策略明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DrawStrategyDetail drawStrategyDetail)
    {
        return toAjax(drawStrategyDetailService.updateDrawStrategyDetail(drawStrategyDetail));
    }

    /**
     * 删除策略明细
     */
    @PreAuthorize("@ss.hasPermi('drawstrategy:drawstrategy:remove')")
    @Log(title = "策略明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(drawStrategyDetailService.deleteDrawStrategyDetailByIds(ids));
    }
}
