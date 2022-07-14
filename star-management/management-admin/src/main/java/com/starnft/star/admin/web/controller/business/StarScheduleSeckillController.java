package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarScheduleSeckill;
import com.starnft.star.business.domain.vo.StarScheduleSeckillVo;
import com.starnft.star.business.service.IStarScheduleSeckillService;
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
 * 秒杀活动Controller
 *
 * @author shellya
 * @date 2022-06-26
 */
@RestController
@RequestMapping("/business/seckill")
public class StarScheduleSeckillController extends BaseController
{
    @Resource
    private IStarScheduleSeckillService starScheduleSeckillService;

    /**
     * 查询秒杀活动列表
     */
    @PreAuthorize("@ss.hasPermi('business:seckill:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarScheduleSeckill starScheduleSeckill)
    {
        startPage();
        List<StarScheduleSeckillVo> list = starScheduleSeckillService.selectStarScheduleSeckillVoList(starScheduleSeckill);
        return getDataTable(list);
    }

    /**
     * 导出秒杀活动列表
     */
    @PreAuthorize("@ss.hasPermi('business:seckill:export')")
    @Log(title = "秒杀活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarScheduleSeckill starScheduleSeckill)
    {
        List<StarScheduleSeckill> list = starScheduleSeckillService.selectStarScheduleSeckillList(starScheduleSeckill);
        ExcelUtil<StarScheduleSeckill> util = new ExcelUtil<StarScheduleSeckill>(StarScheduleSeckill.class);
        util.exportExcel(response, list, "秒杀活动数据");
    }

    /**
     * 获取秒杀活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:seckill:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starScheduleSeckillService.selectStarScheduleSeckillById(id));
    }

    /**
     * 新增秒杀活动
     */
    @PreAuthorize("@ss.hasPermi('business:seckill:add')")
    @Log(title = "秒杀活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarScheduleSeckill starScheduleSeckill)
    {
        return toAjax(starScheduleSeckillService.insertStarScheduleSeckill(starScheduleSeckill,getUserId()));
    }

    /**
     * 修改秒杀活动
     */
    @PreAuthorize("@ss.hasPermi('business:seckill:edit')")
    @Log(title = "秒杀活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarScheduleSeckill starScheduleSeckill)
    {
        return toAjax(starScheduleSeckillService.updateStarScheduleSeckill(starScheduleSeckill));
    }

    /**
     * 删除秒杀活动
     */
    @PreAuthorize("@ss.hasPermi('business:seckill:remove')")
    @Log(title = "秒杀活动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starScheduleSeckillService.deleteStarScheduleSeckillByIds(ids));
    }
}
