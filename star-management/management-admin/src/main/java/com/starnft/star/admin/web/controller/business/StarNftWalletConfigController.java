package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftWalletConfig;
import com.starnft.star.business.service.IStarNftWalletConfigService;
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
 * 钱包规则配置Controller
 *
 * @author shellya
 * @date 2022-06-08
 */
@RestController
@RequestMapping("/business/config")
public class StarNftWalletConfigController extends BaseController
{
    @Resource
    private IStarNftWalletConfigService starNftWalletConfigService;

    /**
     * 查询钱包规则配置列表
     */
    @PreAuthorize("@ss.hasPermi('business:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftWalletConfig starNftWalletConfig)
    {
        startPage();
        List<StarNftWalletConfig> list = starNftWalletConfigService.selectStarNftWalletConfigList(starNftWalletConfig);
        return getDataTable(list);
    }

    /**
     * 导出钱包规则配置列表
     */
    @PreAuthorize("@ss.hasPermi('business:config:export')")
    @Log(title = "钱包规则配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftWalletConfig starNftWalletConfig)
    {
        List<StarNftWalletConfig> list = starNftWalletConfigService.selectStarNftWalletConfigList(starNftWalletConfig);
        ExcelUtil<StarNftWalletConfig> util = new ExcelUtil<StarNftWalletConfig>(StarNftWalletConfig.class);
        util.exportExcel(response, list, "钱包规则配置数据");
    }

    /**
     * 获取钱包规则配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftWalletConfigService.selectStarNftWalletConfigById(id));
    }

    /**
     * 新增钱包规则配置
     */
    @PreAuthorize("@ss.hasPermi('business:config:add')")
    @Log(title = "钱包规则配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftWalletConfig starNftWalletConfig)
    {
        return toAjax(starNftWalletConfigService.insertStarNftWalletConfig(starNftWalletConfig,getLoginUser()));
    }

    /**
     * 修改钱包规则配置
     */
    @PreAuthorize("@ss.hasPermi('business:config:edit')")
    @Log(title = "钱包规则配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftWalletConfig starNftWalletConfig)
    {
        return toAjax(starNftWalletConfigService.updateStarNftWalletConfig(starNftWalletConfig,getLoginUser()));
    }

    /**
     * 删除钱包规则配置
     */
    @PreAuthorize("@ss.hasPermi('business:config:remove')")
    @Log(title = "钱包规则配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftWalletConfigService.deleteStarNftWalletConfigByIds(ids));
    }
}
