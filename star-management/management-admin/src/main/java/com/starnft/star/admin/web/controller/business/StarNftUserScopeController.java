package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftUserScope;
import com.starnft.star.business.service.IStarNftUserScopeService;
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
 * 用户积分Controller
 *
 * @author shellya
 * @date 2022-06-25
 */
@RestController
@RequestMapping("/business/scope")
public class StarNftUserScopeController extends BaseController
{
    @Resource
    private IStarNftUserScopeService starNftUserScopeService;

    /**
     * 查询用户积分列表
     */
    @PreAuthorize("@ss.hasPermi('business:scope:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftUserScope starNftUserScope)
    {
        startPage();
        List<StarNftUserScope> list = starNftUserScopeService.selectStarNftUserScopeList(starNftUserScope);
        return getDataTable(list);
    }

    /**
     * 导出用户积分列表
     */
    @PreAuthorize("@ss.hasPermi('business:scope:export')")
    @Log(title = "用户积分", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftUserScope starNftUserScope)
    {
        List<StarNftUserScope> list = starNftUserScopeService.selectStarNftUserScopeList(starNftUserScope);
        ExcelUtil<StarNftUserScope> util = new ExcelUtil<StarNftUserScope>(StarNftUserScope.class);
        util.exportExcel(response, list, "用户积分数据");
    }

    /**
     * 获取用户积分详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:scope:all')")
    @GetMapping(value = "/all/{id}")
    public AjaxResult getAllScope(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftUserScopeService.selectScopeByAccount(id));
    }


    /**
     * 获取用户所有积分详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:scope:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftUserScopeService.selectStarNftUserScopeById(id));
    }

//    /**
//     * 新增用户积分
//     */
//    @PreAuthorize("@ss.hasPermi('business:scope:add')")
//    @Log(title = "用户积分", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody StarNftUserScope starNftUserScope)
//    {
//        return toAjax(starNftUserScopeService.insertStarNftUserScope(starNftUserScope));
//    }

    /**
     * 修改用户积分
     */
    @PreAuthorize("@ss.hasPermi('business:scope:edit')")
    @Log(title = "用户积分", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftUserScope starNftUserScope)
    {
        return toAjax(starNftUserScopeService.updateStarNftUserScope(starNftUserScope));
    }

//    /**
//     * 删除用户积分
//     */
//    @PreAuthorize("@ss.hasPermi('business:scope:remove')")
//    @Log(title = "用户积分", businessType = BusinessType.DELETE)
//	@DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids)
//    {
//        return toAjax(starNftUserScopeService.deleteStarNftUserScopeByIds(ids));
//    }
}
