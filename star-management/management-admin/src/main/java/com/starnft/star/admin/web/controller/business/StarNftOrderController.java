package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftOrder;
import com.starnft.star.business.service.IStarNftOrderService;
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
 * 订单Controller
 *
 * @author shellya
 * @date 2022-05-28
 */
@RestController
@RequestMapping("/business/order")
public class StarNftOrderController extends BaseController
{
    @Resource
    private IStarNftOrderService starNftOrderService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('business:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftOrder starNftOrder)
    {
        startPage();
        List<StarNftOrder> list = starNftOrderService.selectStarNftOrderList(starNftOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('business:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftOrder starNftOrder)
    {
        List<StarNftOrder> list = starNftOrderService.selectStarNftOrderList(starNftOrder);
        ExcelUtil<StarNftOrder> util = new ExcelUtil<StarNftOrder>(StarNftOrder.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftOrderService.selectStarNftOrderById(id));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('business:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftOrder starNftOrder)
    {
        return toAjax(starNftOrderService.insertStarNftOrder(starNftOrder));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('business:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftOrder starNftOrder)
    {
        return toAjax(starNftOrderService.updateStarNftOrder(starNftOrder));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('business:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftOrderService.deleteStarNftOrderByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('business:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PostMapping(value = "refund")
    public AjaxResult refund(String orderSn){
        return toAjax(starNftOrderService.refundOrder(orderSn));
    }
}
