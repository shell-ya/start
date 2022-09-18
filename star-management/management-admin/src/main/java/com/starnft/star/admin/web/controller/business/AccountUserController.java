package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.AccountUser;
import com.starnft.star.business.domain.StarNftThemeInfo;
import com.starnft.star.business.service.IAccountUserService;
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
 * 用户Controller
 *
 * @author shellya
 * @date 2022-05-28
 */
@RestController
@RequestMapping("/business/user")
public class AccountUserController extends BaseController
{
    @Resource
    private IAccountUserService accountUserService;
    /**
     * 查询用户列表
     */
    @PreAuthorize("@ss.hasPermi('business:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(AccountUser accountUser)
    {
        startPage();
        List<AccountUser> list = accountUserService.selectAccountUserList(accountUser);
        return getDataTable(list);
    }

    /**
     * 导出用户列表
     */
    @PreAuthorize("@ss.hasPermi('business:user:export')")
    @Log(title = "用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AccountUser accountUser)
    {
        List<AccountUser> list = accountUserService.selectAccountUserList(accountUser);
        ExcelUtil<AccountUser> util = new ExcelUtil<AccountUser>(AccountUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    /**
     * 获取用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(accountUserService.selectAccountUserById(id));
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('business:user:add')")
    @Log(title = "用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AccountUser accountUser)
    {
        return toAjax(accountUserService.insertAccountUser(accountUser));
    }

    @PreAuthorize("@ss.hasPermi('business:user:add')")
    @Log(title = "用户", businessType = BusinessType.INSERT)
    @PostMapping("/getUserId")
    public AjaxResult getUserId(@RequestBody String[] phones)
    {
        return AjaxResult.success(accountUserService.queryUserId(phones));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('business:user:edit')")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AccountUser accountUser)
    {
        return toAjax(accountUserService.updateAccountUser(accountUser));
    }
    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('business:user:remove')")
    @Log(title = "用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(accountUserService.deleteAccountUserByIds(ids));
    }
}
