package com.starnft.star.admin.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.enums.BusinessType;
import com.starnft.star.business.domain.StarNftCompose;
import com.starnft.star.business.service.IStarNftComposeService;
import com.starnft.star.common.utils.poi.ExcelUtil;
import com.starnft.star.common.core.page.TableDataInfo;

/**
 * 合成活动Controller
 *
 * @author ruoyi
 * @date 2022-07-30
 */
@RestController
@RequestMapping("/business/compose")
public class StarNftComposeController extends BaseController
{
    @Autowired
    private IStarNftComposeService starNftComposeService;

    /**
     * 查询合成活动列表
     */
    @PreAuthorize("@ss.hasPermi('business:compose:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftCompose starNftCompose)
    {
        startPage();
        List<StarNftCompose> list = starNftComposeService.selectStarNftComposeList(starNftCompose);
        return getDataTable(list);
    }

    /**
     * 导出合成活动列表
     */
    @PreAuthorize("@ss.hasPermi('business:compose:export')")
    @Log(title = "合成活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftCompose starNftCompose)
    {
        List<StarNftCompose> list = starNftComposeService.selectStarNftComposeList(starNftCompose);
        ExcelUtil<StarNftCompose> util = new ExcelUtil<StarNftCompose>(StarNftCompose.class);
        util.exportExcel(response, list, "合成活动数据");
    }

    /**
     * 获取合成活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:compose:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftComposeService.selectStarNftComposeById(id));
    }

    /**
     * 新增合成活动
     */
    @PreAuthorize("@ss.hasPermi('business:compose:add')")
    @Log(title = "合成活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftCompose starNftCompose)
    {
        return toAjax(starNftComposeService.insertStarNftCompose(starNftCompose));
    }

    /**
     * 修改合成活动
     */
    @PreAuthorize("@ss.hasPermi('business:compose:edit')")
    @Log(title = "合成活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftCompose starNftCompose)
    {
        return toAjax(starNftComposeService.updateStarNftCompose(starNftCompose));
    }

    /**
     * 删除合成活动
     */
    @PreAuthorize("@ss.hasPermi('business:compose:remove')")
    @Log(title = "合成活动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftComposeService.deleteStarNftComposeByIds(ids));
    }
}
