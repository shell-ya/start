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
import com.starnft.star.business.domain.StarBulletin;
import com.starnft.star.business.service.IStarBulletinService;

/**
 * 公告Controller
 *
 * @author shellya
 * @date 2022-07-25
 */
@RestController
@RequestMapping("/business/bulletin")
public class StarBulletinController extends BaseController
{
    @Autowired
    private IStarBulletinService starBulletinService;

    /**
     * 查询公告列表
     */
    @PreAuthorize("@ss.hasPermi('business:bulletin:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarBulletin starBulletin)
    {
        startPage();
        List<StarBulletin> list = starBulletinService.selectStarBulletinList(starBulletin);
        return getDataTable(list);
    }

    /**
     * 导出公告列表
     */
//    @PreAuthorize("@ss.hasPermi('business:bulletin:export')")
//    @Log(title = "公告", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, StarBulletin starBulletin)
//    {
//        List<StarBulletin> list = starBulletinService.selectStarBulletinList(starBulletin);
//        ExcelUtil<StarBulletin> util = new ExcelUtil<StarBulletin>(StarBulletin.class);
//        util.exportExcel(response, list, "公告数据");
//    }

    /**
     * 获取公告详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:bulletin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starBulletinService.selectStarBulletinById(id));
    }

    /**
     * 新增公告
     */
    @PreAuthorize("@ss.hasPermi('business:bulletin:add')")
    @Log(title = "公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarBulletin starBulletin)
    {
        return toAjax(starBulletinService.insertStarBulletin(starBulletin));
    }

    /**
     * 修改公告
     */
    @PreAuthorize("@ss.hasPermi('business:bulletin:edit')")
    @Log(title = "公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarBulletin starBulletin)
    {
        return toAjax(starBulletinService.updateStarBulletin(starBulletin));
    }

    /**
     * 删除公告
     */
    @PreAuthorize("@ss.hasPermi('business:bulletin:remove')")
    @Log(title = "公告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starBulletinService.deleteStarBulletinByIds(ids));
    }
}
