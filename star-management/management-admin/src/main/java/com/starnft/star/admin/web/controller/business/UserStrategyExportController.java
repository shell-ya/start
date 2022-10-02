package com.starnft.star.admin.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.page.TableDataInfo;
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
import com.starnft.star.common.annotation.Log;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.enums.BusinessType;
import com.starnft.star.business.domain.UserStrategyExport;
import com.starnft.star.business.service.IUserStrategyExportService;


/**
 * 用户策略计算结果Controller
 *
 * @author ruoyi
 * @date 2022-09-29
 */
@RestController
@RequestMapping("/business/userexport")
public class UserStrategyExportController extends BaseController
{
    @Autowired
    private IUserStrategyExportService userStrategyExportService;

    /**
     * 查询用户策略计算结果列表
     */
    @PreAuthorize("@ss.hasPermi('business:userexport:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserStrategyExport userStrategyExport)
    {
        startPage();
        List<UserStrategyExport> list = userStrategyExportService.selectUserStrategyExportList(userStrategyExport);
        return getDataTable(list);
    }

    /**
     * 导出用户策略计算结果列表
     */
    @PreAuthorize("@ss.hasPermi('business:userexport:export')")
    @Log(title = "用户策略计算结果", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserStrategyExport userStrategyExport)
    {
        List<UserStrategyExport> list = userStrategyExportService.selectUserStrategyExportList(userStrategyExport);
        ExcelUtil<UserStrategyExport> util = new ExcelUtil<UserStrategyExport>(UserStrategyExport.class);
        util.exportExcel(response, list, "用户策略计算结果数据");
    }

    /**
     * 获取用户策略计算结果详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:userexport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userStrategyExportService.selectUserStrategyExportById(id));
    }

    /**
     * 新增用户策略计算结果
     */
    @PreAuthorize("@ss.hasPermi('business:userexport:add')")
    @Log(title = "用户策略计算结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserStrategyExport userStrategyExport)
    {
        return toAjax(userStrategyExportService.insertUserStrategyExport(userStrategyExport));
    }

    /**
     * 修改用户策略计算结果
     */
    @PreAuthorize("@ss.hasPermi('business:userexport:edit')")
    @Log(title = "用户策略计算结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserStrategyExport userStrategyExport)
    {
        return toAjax(userStrategyExportService.updateUserStrategyExport(userStrategyExport));
    }

    /**
     * 删除用户策略计算结果
     */
    @PreAuthorize("@ss.hasPermi('business:userexport:remove')")
    @Log(title = "用户策略计算结果", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userStrategyExportService.deleteUserStrategyExportByIds(ids));
    }
}
