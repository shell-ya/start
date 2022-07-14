package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftScopeRecord;
import com.starnft.star.business.service.IStarNftScopeRecordService;
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
 * 积分数据Controller
 *
 * @author shellya
 * @date 2022-06-25
 */
@RestController
@RequestMapping("/business/record")
public class StarNftScopeRecordController extends BaseController
{
    @Resource
    private IStarNftScopeRecordService starNftScopeRecordService;

    /**
     * 查询积分数据列表
     */
    @PreAuthorize("@ss.hasPermi('business:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftScopeRecord starNftScopeRecord)
    {
        startPage();
        List<StarNftScopeRecord> list = starNftScopeRecordService.selectStarNftScopeRecordList(starNftScopeRecord);
        return getDataTable(list);
    }

    /**
     * 导出积分数据列表
     */
    @PreAuthorize("@ss.hasPermi('business:record:export')")
    @Log(title = "积分数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftScopeRecord starNftScopeRecord)
    {
        List<StarNftScopeRecord> list = starNftScopeRecordService.selectStarNftScopeRecordList(starNftScopeRecord);
        ExcelUtil<StarNftScopeRecord> util = new ExcelUtil<StarNftScopeRecord>(StarNftScopeRecord.class);
        util.exportExcel(response, list, "积分数据数据");
    }

    /**
     * 获取积分数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftScopeRecordService.selectStarNftScopeRecordById(id));
    }

//    /**
//     * 新增积分数据
//     */
//    @PreAuthorize("@ss.hasPermi('business:record:add')")
//    @Log(title = "积分数据", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody StarNftScopeRecord starNftScopeRecord)
//    {
//        return toAjax(starNftScopeRecordService.insertStarNftScopeRecord(starNftScopeRecord));
//    }

//    /**
//     * 修改积分数据
//     */
//    @PreAuthorize("@ss.hasPermi('business:record:edit')")
//    @Log(title = "积分数据", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody StarNftScopeRecord starNftScopeRecord)
//    {
//        return toAjax(starNftScopeRecordService.updateStarNftScopeRecord(starNftScopeRecord));
//    }

//    /**
//     * 删除积分数据
//     */
//    @PreAuthorize("@ss.hasPermi('business:record:remove')")
//    @Log(title = "积分数据", businessType = BusinessType.DELETE)
//	@DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids)
//    {
//        return toAjax(starNftScopeRecordService.deleteStarNftScopeRecordByIds(ids));
//    }
}
