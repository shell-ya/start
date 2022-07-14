package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftDict;
import com.starnft.star.business.service.IStarNftDictService;
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
 * 字典Controller
 *
 * @author shellya
 * @date 2022-06-08
 */
@RestController
@RequestMapping("/business/dict")
public class StarNftDictController extends BaseController
{
    @Resource
    private IStarNftDictService starNftDictService;

    /**
     * 查询字典列表
     */
    @PreAuthorize("@ss.hasPermi('business:dict:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftDict starNftDict)
    {
        startPage();
        List<StarNftDict> list = starNftDictService.selectStarNftDictList(starNftDict);
        return getDataTable(list);
    }

    /**
     * 导出字典列表
     */
    @PreAuthorize("@ss.hasPermi('business:dict:export')")
    @Log(title = "字典", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftDict starNftDict)
    {
        List<StarNftDict> list = starNftDictService.selectStarNftDictList(starNftDict);
        ExcelUtil<StarNftDict> util = new ExcelUtil<StarNftDict>(StarNftDict.class);
        util.exportExcel(response, list, "字典数据");
    }

    /**
     * 获取字典详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:dict:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftDictService.selectStarNftDictById(id));
    }

    /**
     * 新增字典
     */
    @PreAuthorize("@ss.hasPermi('business:dict:add')")
    @Log(title = "字典", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftDict starNftDict)
    {

        return toAjax(starNftDictService.insertStarNftDict(starNftDict,getLoginUser()));
    }

    /**
     * 修改字典
     */
    @PreAuthorize("@ss.hasPermi('business:dict:edit')")
    @Log(title = "字典", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftDict starNftDict)
    {
        return toAjax(starNftDictService.updateStarNftDict(starNftDict,getLoginUser()));
    }

    /**
     * 删除字典
     */
    @PreAuthorize("@ss.hasPermi('business:dict:remove')")
    @Log(title = "字典", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftDictService.deleteStarNftDictByIds(ids));
    }
}
