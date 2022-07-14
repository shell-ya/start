package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftThemeNumber;
import com.starnft.star.business.service.IStarNftThemeNumberService;
import com.starnft.star.common.annotation.Log;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.core.page.TableDataInfo;
import com.starnft.star.common.enums.BusinessType;
import com.starnft.star.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 主题编号Controller
 *
 * @author shellya
 * @date 2022-06-03
 */
@RestController
@RequestMapping("/business/number")
public class StarNftThemeNumberController extends BaseController
{
    @Resource
    private IStarNftThemeNumberService starNftThemeNumberService;

    /**
     * 查询主题编号列表
     */
    @PreAuthorize("@ss.hasPermi('business:number:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftThemeNumber starNftThemeNumber)
    {
        startPage();
//        List<StarNftThemeNumberVo> list = starNftThemeNumberService.selectStarNftThemeNumberVoList(starNftThemeNumber);
        List<StarNftThemeNumber> list = starNftThemeNumberService.selectStarNftThemeNumberList(starNftThemeNumber);
        return getDataTable(list);
    }

    /**
     * 导出主题编号列表
     */
    @PreAuthorize("@ss.hasPermi('business:number:export')")
    @Log(title = "主题编号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftThemeNumber starNftThemeNumber) throws IOException {
        List<StarNftThemeNumber> list = starNftThemeNumberService.selectStarNftThemeNumberList(starNftThemeNumber);
        ExcelUtil<StarNftThemeNumber> util = new ExcelUtil<StarNftThemeNumber>(StarNftThemeNumber.class);
        util.exportEasyExcel(response, list, "主题编号数据");
    }

    /**
     * 获取主题编号详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:number:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftThemeNumberService.selectStarNftThemeNumberById(id));
    }

    /**
     * 新增主题编号
     */
    @PreAuthorize("@ss.hasPermi('business:number:add')")
    @Log(title = "主题编号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftThemeNumber starNftThemeNumber)
    {
        return toAjax(starNftThemeNumberService.insertStarNftThemeNumber(starNftThemeNumber,getUserId().toString()));
    }

    /**
     * 修改主题编号
     */
    @PreAuthorize("@ss.hasPermi('business:number:edit')")
    @Log(title = "主题编号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftThemeNumber starNftThemeNumber)
    {
        return toAjax(starNftThemeNumberService.updateStarNftThemeNumber(starNftThemeNumber));
    }

    /**
     * 删除主题编号
     */
    @PreAuthorize("@ss.hasPermi('business:number:remove')")
    @Log(title = "主题编号", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftThemeNumberService.deleteStarNftThemeNumberByIds(ids));
    }

    @Log(title = "主题商品管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('business:number:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, Long themeId) throws Exception
    {
        ExcelUtil<StarNftThemeNumber> util = new ExcelUtil<StarNftThemeNumber>(StarNftThemeNumber.class);
        List<StarNftThemeNumber> themeNumberList = util.importExcel(file.getInputStream());
        String operName = getUserId().toString();
        String message = starNftThemeNumberService.importThemeNumber(themeNumberList, themeId, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<StarNftThemeNumber> util = new ExcelUtil<>(StarNftThemeNumber.class);
        util.importTemplateExcel(response, "主题作品模版");
    }
}
