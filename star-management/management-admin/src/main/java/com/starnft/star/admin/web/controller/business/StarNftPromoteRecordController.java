package com.starnft.star.admin.web.controller.business;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.starnft.star.business.service.IPromoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.starnft.star.common.annotation.Log;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.enums.BusinessType;
import com.starnft.star.business.domain.StarNftPromoteRecord;
import com.starnft.star.business.service.IStarNftPromoteRecordService;
import com.starnft.star.common.utils.poi.ExcelUtil;
import com.starnft.star.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 推广记录Controller
 *
 * @author shellya
 * @date 2022-07-23
 */
@RestController
@Api("推广管理")
@RequestMapping("/promote/promoteRecord")
public class StarNftPromoteRecordController extends BaseController
{
    @Autowired
    private IStarNftPromoteRecordService starNftPromoteRecordService;

    @Resource
    IPromoteService promoteService;
    @PostMapping("promoteExcel")
    @SneakyThrows
    @ApiOperation(tags = "Excel导入推广",value = "Excel导入推广")
    public AjaxResult promoteExcel(@RequestParam("file") MultipartFile file, @RequestParam("context") String context, @RequestParam("types") Integer types){
        return   AjaxResult.success( promoteService.promoteExcel(file.getInputStream(),context,types));
    }
    /**
     * 查询推广记录列表
     */
    @PreAuthorize("@ss.hasPermi('promote:promoteRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftPromoteRecord starNftPromoteRecord)
    {
        startPage();
        List<StarNftPromoteRecord> list = starNftPromoteRecordService.selectStarNftPromoteRecordList(starNftPromoteRecord);
        return getDataTable(list);
    }

    /**
     * 导出推广记录列表
     */
    @PreAuthorize("@ss.hasPermi('promote:promoteRecord:export')")
    @Log(title = "推广记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftPromoteRecord starNftPromoteRecord)
    {
        List<StarNftPromoteRecord> list = starNftPromoteRecordService.selectStarNftPromoteRecordList(starNftPromoteRecord);
        ExcelUtil<StarNftPromoteRecord> util = new ExcelUtil<StarNftPromoteRecord>(StarNftPromoteRecord.class);
        util.exportExcel(response, list, "推广记录数据");
    }

    /**
     * 获取推广记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('promote:promoteRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftPromoteRecordService.selectStarNftPromoteRecordById(id));
    }

    /**
     * 新增推广记录
     */
    @PreAuthorize("@ss.hasPermi('promote:promoteRecord:add')")
    @Log(title = "推广记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftPromoteRecord starNftPromoteRecord)
    {
        return toAjax(starNftPromoteRecordService.insertStarNftPromoteRecord(starNftPromoteRecord));
    }

    /**
     * 修改推广记录
     */
    @PreAuthorize("@ss.hasPermi('promote:promoteRecord:edit')")
    @Log(title = "推广记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftPromoteRecord starNftPromoteRecord)
    {
        return toAjax(starNftPromoteRecordService.updateStarNftPromoteRecord(starNftPromoteRecord));
    }

    /**
     * 删除推广记录
     */
    @PreAuthorize("@ss.hasPermi('promote:promoteRecord:remove')")
    @Log(title = "推广记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftPromoteRecordService.deleteStarNftPromoteRecordByIds(ids));
    }
}
