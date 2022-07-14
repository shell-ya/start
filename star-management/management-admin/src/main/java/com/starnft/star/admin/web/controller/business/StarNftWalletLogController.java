package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftWalletLog;
import com.starnft.star.business.service.IStarNftWalletLogService;
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
 * 钱包变化记录Controller
 *
 * @author shellya
 * @date 2022-06-09
 */
@RestController
@RequestMapping("/business/log")
public class StarNftWalletLogController extends BaseController
{
    @Resource
    private IStarNftWalletLogService starNftWalletLogService;

    /**
     * 查询钱包变化记录列表
     */
    @PreAuthorize("@ss.hasPermi('business:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftWalletLog starNftWalletLog)
    {
        startPage();
        List<StarNftWalletLog> list = starNftWalletLogService.selectStarNftWalletLogList(starNftWalletLog);
        return getDataTable(list);
    }

    /**
     * 导出钱包变化记录列表
     */
    @PreAuthorize("@ss.hasPermi('business:log:export')")
    @Log(title = "钱包变化记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftWalletLog starNftWalletLog)
    {
        List<StarNftWalletLog> list = starNftWalletLogService.selectStarNftWalletLogList(starNftWalletLog);
        ExcelUtil<StarNftWalletLog> util = new ExcelUtil<StarNftWalletLog>(StarNftWalletLog.class);
        util.exportExcel(response, list, "钱包变化记录数据");
    }

    /**
     * 获取钱包变化记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftWalletLogService.selectStarNftWalletLogById(id));
    }

    /**
     * 新增钱包变化记录
     */
    @PreAuthorize("@ss.hasPermi('business:log:add')")
    @Log(title = "钱包变化记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftWalletLog starNftWalletLog)
    {
        return toAjax(starNftWalletLogService.insertStarNftWalletLog(starNftWalletLog));
    }

    /**
     * 修改钱包变化记录
     */
    @PreAuthorize("@ss.hasPermi('business:log:edit')")
    @Log(title = "钱包变化记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftWalletLog starNftWalletLog)
    {
        return toAjax(starNftWalletLogService.updateStarNftWalletLog(starNftWalletLog));
    }

    /**
     * 删除钱包变化记录
     */
    @PreAuthorize("@ss.hasPermi('business:log:remove')")
    @Log(title = "钱包变化记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftWalletLogService.deleteStarNftWalletLogByIds(ids));
    }
}
