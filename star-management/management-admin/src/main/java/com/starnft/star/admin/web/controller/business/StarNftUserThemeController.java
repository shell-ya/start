package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftUserTheme;
import com.starnft.star.business.domain.vo.UserSeriesVO;
import com.starnft.star.business.service.IStarNftUserThemeService;
import com.starnft.star.common.annotation.Log;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.core.page.TableDataInfo;
import com.starnft.star.common.enums.BusinessType;
import com.starnft.star.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 用户藏品Controller
 *
 * @author ruoyi
 * @date 2022-07-20
 */
@RestController
@RequestMapping("/usertheme/theme")
public class StarNftUserThemeController extends BaseController
{
    @Autowired
    private IStarNftUserThemeService starNftUserThemeService;

    /**
     * 查询用户藏品列表
     */
    @PreAuthorize("@ss.hasPermi('usertheme:theme:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftUserTheme starNftUserTheme)
    {
        startPage();
        List<StarNftUserTheme> list = starNftUserThemeService.selectStarNftUserThemeList(starNftUserTheme);
        return getDataTable(list);
    }
    @PreAuthorize("@ss.hasPermi('usertheme:theme:listSeries')")
    @PostMapping("/listSeries/{id}")
    public AjaxResult listSeries(@PathVariable("id") String  id)
    {
        Map<Integer, Map<Long, Optional<UserSeriesVO>>>  list = starNftUserThemeService.listSeriesByUserId(id);
        return AjaxResult.success(list);
    }

    @PreAuthorize("@ss.hasPermi('usertheme:theme:listThemeBySeriesAndAccount')")
    @PostMapping("/listTheme")
    public AjaxResult listThemeBySeriesAndAccount(@RequestBody  StarNftUserTheme starNftUserTheme)
    {
        Object o = starNftUserThemeService.listThemeBySeriesAndAccount(starNftUserTheme);
        return AjaxResult.success(o);
    }
    /**
     * 导出用户藏品列表
     */
    @PreAuthorize("@ss.hasPermi('usertheme:theme:export')")
    @Log(title = "用户藏品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftUserTheme starNftUserTheme)
    {
        List<StarNftUserTheme> list = starNftUserThemeService.selectStarNftUserThemeList(starNftUserTheme);
        ExcelUtil<StarNftUserTheme> util = new ExcelUtil<StarNftUserTheme>(StarNftUserTheme.class);
        util.exportExcel(response, list, "用户藏品数据");
    }

    /**
     * 获取用户藏品详细信息
     */
    @PreAuthorize("@ss.hasPermi('usertheme:theme:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftUserThemeService.selectStarNftUserThemeById(id));
    }

    /**
     * 新增用户藏品
     */
    @PreAuthorize("@ss.hasPermi('usertheme:theme:add')")
    @Log(title = "用户藏品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftUserTheme starNftUserTheme)
    {
        return toAjax(starNftUserThemeService.insertStarNftUserTheme(starNftUserTheme));
    }

    /**
     * 修改用户藏品
     */
    @PreAuthorize("@ss.hasPermi('usertheme:theme:edit')")
    @Log(title = "用户藏品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftUserTheme starNftUserTheme)
    {
        return toAjax(starNftUserThemeService.updateStarNftUserTheme(starNftUserTheme));
    }

    /**
     * 删除用户藏品
     */
    @PreAuthorize("@ss.hasPermi('usertheme:theme:remove')")
    @Log(title = "用户藏品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftUserThemeService.deleteStarNftUserThemeByIds(ids));
    }
}
