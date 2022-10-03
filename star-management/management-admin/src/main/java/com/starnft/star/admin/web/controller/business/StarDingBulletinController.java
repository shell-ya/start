package com.starnft.star.admin.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.starnft.star.common.annotation.Log;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.core.page.TableDataInfo;
import com.starnft.star.common.enums.BusinessType;
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

import com.starnft.star.business.domain.StarDingBulletin;
import com.starnft.star.business.service.IStarDingBulletinService;


/**
 * 盯链公告Controller
 *
 * @author zz
 * @date 2022-10-03
 */
@RestController
@RequestMapping("/dingbulletin/dingbulletin")
public class StarDingBulletinController extends BaseController
{
    @Autowired
    private IStarDingBulletinService starDingBulletinService;

    /**
     * 查询盯链公告列表
     */
    @PreAuthorize("@ss.hasPermi('dingbulletin:dingbulletin:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarDingBulletin starDingBulletin)
    {
        startPage();
        List<StarDingBulletin> list = starDingBulletinService.selectStarDingBulletinList(starDingBulletin);
        return getDataTable(list);
    }

    /**
     * 导出盯链公告列表
     */
    @PreAuthorize("@ss.hasPermi('dingbulletin:dingbulletin:export')")
    @Log(title = "盯链公告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarDingBulletin starDingBulletin)
    {
        List<StarDingBulletin> list = starDingBulletinService.selectStarDingBulletinList(starDingBulletin);
        ExcelUtil<StarDingBulletin> util = new ExcelUtil<StarDingBulletin>(StarDingBulletin.class);
        util.exportExcel(response, list, "盯链公告数据");
    }

    /**
     * 获取盯链公告详细信息
     */
    @PreAuthorize("@ss.hasPermi('dingbulletin:dingbulletin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starDingBulletinService.selectStarDingBulletinById(id));
    }

    /**
     * 新增盯链公告
     */
    @PreAuthorize("@ss.hasPermi('dingbulletin:dingbulletin:add')")
    @Log(title = "盯链公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarDingBulletin starDingBulletin)
    {
        return toAjax(starDingBulletinService.insertStarDingBulletin(starDingBulletin));
    }

    /**
     * 修改盯链公告
     */
    @PreAuthorize("@ss.hasPermi('dingbulletin:dingbulletin:edit')")
    @Log(title = "盯链公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarDingBulletin starDingBulletin)
    {
        return toAjax(starDingBulletinService.updateStarDingBulletin(starDingBulletin));
    }

    /**
     * 删除盯链公告
     */
    @PreAuthorize("@ss.hasPermi('dingbulletin:dingbulletin:remove')")
    @Log(title = "盯链公告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starDingBulletinService.deleteStarDingBulletinByIds(ids));
    }
}
