package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftPublisher;
import com.starnft.star.business.service.IStarNftPublisherService;
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
 * 工作室Controller
 *
 * @author shellya
 * @date 2022-06-26
 */
@RestController
@RequestMapping("/business/publisher")
public class StarNftPublisherController extends BaseController {
    @Resource
    private IStarNftPublisherService starNftPublisherService;

    /**
     * 查询工作室列表
     */
    @PreAuthorize("@ss.hasPermi('business:publisher:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftPublisher starNftPublisher) {
        startPage();
        List<StarNftPublisher> list = starNftPublisherService.selectStarNftPublisherList(starNftPublisher);
        return getDataTable(list);
    }

    /**
     * 导出工作室列表
     */
    @PreAuthorize("@ss.hasPermi('business:publisher:export')")
    @Log(title = "工作室", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftPublisher starNftPublisher) {
        List<StarNftPublisher> list = starNftPublisherService.selectStarNftPublisherList(starNftPublisher);
        ExcelUtil<StarNftPublisher> util = new ExcelUtil<StarNftPublisher>(StarNftPublisher.class);
        util.exportExcel(response, list, "工作室数据");
    }

    /**
     * 获取工作室详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:publisher:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(starNftPublisherService.selectStarNftPublisherById(id));
    }

    /**
     * 新增工作室
     */
    @PreAuthorize("@ss.hasPermi('business:publisher:add')")
    @Log(title = "工作室", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftPublisher starNftPublisher) {
        return toAjax(starNftPublisherService.insertStarNftPublisher(starNftPublisher));
    }

    /**
     * 修改工作室
     */
    @PreAuthorize("@ss.hasPermi('business:publisher:edit')")
    @Log(title = "工作室", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftPublisher starNftPublisher) {
        return toAjax(starNftPublisherService.updateStarNftPublisher(starNftPublisher));
    }

    /**
     * 删除工作室
     */
    @PreAuthorize("@ss.hasPermi('business:publisher:remove')")
    @Log(title = "工作室", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(starNftPublisherService.deleteStarNftPublisherByIds(ids));
    }
    @PreAuthorize("@ss.hasPermi('business:publisher:tree')")
    @PostMapping("/tree")
    public AjaxResult tree(@RequestBody StarNftPublisher starNftPublisher){
        return AjaxResult.success(starNftPublisherService.treeSelect(starNftPublisher));
    }

    @PreAuthorize("@ss.hasPermi('business:publisher:select')")
    @GetMapping("/select")
    public AjaxResult select(){
        return AjaxResult.success(starNftPublisherService.selectAllPublisher());
    }
}
