package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftWithdrawApply;
import com.starnft.star.business.service.IStarNftWithdrawApplyService;
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
import java.io.IOException;
import java.util.List;

/**
 * 提现申请Controller
 *
 * @author shellya
 * @date 2022-05-28
 */
@RestController
@RequestMapping("/business/apply")
public class StarNftWithdrawApplyController extends BaseController
{
    @Resource
    private IStarNftWithdrawApplyService starNftWithdrawApplyService;

    /**
     * 查询提现申请列表
     */
    @PreAuthorize("@ss.hasPermi('business:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftWithdrawApply starNftWithdrawApply)
    {
        startPage();
        List<StarNftWithdrawApply> list = starNftWithdrawApplyService.selectStarNftWithdrawApplyList(starNftWithdrawApply);
        return getDataTable(list);
    }

    /**
     * 导出提现申请列表
     */
    @PreAuthorize("@ss.hasPermi('business:apply:export')")
    @Log(title = "提现申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftWithdrawApply starNftWithdrawApply) throws IOException {
        List<StarNftWithdrawApply> list = starNftWithdrawApplyService.selectStarNftWithdrawApplyList(starNftWithdrawApply);
        ExcelUtil<StarNftWithdrawApply> util = new ExcelUtil<StarNftWithdrawApply>(StarNftWithdrawApply.class);
        util.exportEasyExcel(response, list, "提现申请数据");
    }

    /**
     * 获取提现申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:apply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftWithdrawApplyService.selectStarNftWithdrawApplyById(id));
    }

    /**
     * 新增提现申请
     */
    @PreAuthorize("@ss.hasPermi('business:apply:add')")
    @Log(title = "提现申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftWithdrawApply starNftWithdrawApply)
    {
        return toAjax(starNftWithdrawApplyService.insertStarNftWithdrawApply(starNftWithdrawApply));
    }

    /**
     * 修改提现申请
     */
    @PreAuthorize("@ss.hasPermi('business:apply:edit')")
    @Log(title = "提现申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftWithdrawApply starNftWithdrawApply)
    {
        return toAjax(starNftWithdrawApplyService.updateStarNftWithdrawApply(starNftWithdrawApply,getLoginUser()));
    }

    /**
     * 删除提现申请
     */
    @PreAuthorize("@ss.hasPermi('business:apply:remove')")
    @Log(title = "提现申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftWithdrawApplyService.deleteStarNftWithdrawApplyByIds(ids));
    }
}
