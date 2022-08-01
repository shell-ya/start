package com.starnft.star.admin.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.starnft.star.business.domain.vo.StarNftComposeCategoryVO;
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
import com.starnft.star.business.domain.StarNftComposeCategory;
import com.starnft.star.business.service.IStarNftComposeCategoryService;
import com.starnft.star.common.utils.poi.ExcelUtil;
import com.starnft.star.common.core.page.TableDataInfo;

/**
 * 合成素材Controller
 *
 * @author ruoyi
 * @date 2022-07-30
 */
@RestController
@RequestMapping("/business/composecategory")
public class StarNftComposeCategoryController extends BaseController
{
    @Autowired
    private IStarNftComposeCategoryService starNftComposeCategoryService;

    /**
     * 查询合成素材列表
     */
    @PreAuthorize("@ss.hasPermi('business:composecategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftComposeCategory starNftComposeCategory)
    {
        startPage();
        List<StarNftComposeCategoryVO> list = starNftComposeCategoryService.selectStarNftComposeCategoryList(starNftComposeCategory);
        return getDataTable(list);
    }

    /**
     * 导出合成素材列表
     */
//    @PreAuthorize("@ss.hasPermi('business:composecategory:export')")
//    @Log(title = "合成素材", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, StarNftComposeCategory starNftComposeCategory)
//    {
//        List<StarNftComposeCategoryVO> list = starNftComposeCategoryService.selectStarNftComposeCategoryList(starNftComposeCategory);
//        ExcelUtil<StarNftComposeCategory> util = new ExcelUtil<StarNftComposeCategory>(StarNftComposeCategory.class);
//        util.exportExcel(response, list, "合成素材数据");
//    }

    /**
     * 获取合成素材详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:composecategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftComposeCategoryService.selectStarNftComposeCategoryById(id));
    }

    /**
     * 新增合成素材
     */
    @PreAuthorize("@ss.hasPermi('business:composecategory:add')")
    @Log(title = "合成素材", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftComposeCategory starNftComposeCategory)
    {
        return toAjax(starNftComposeCategoryService.insertStarNftComposeCategory(starNftComposeCategory));
    }

    /**
     * 修改合成素材
     */
    @PreAuthorize("@ss.hasPermi('business:composecategory:edit')")
    @Log(title = "合成素材", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftComposeCategory starNftComposeCategory)
    {
        return toAjax(starNftComposeCategoryService.updateStarNftComposeCategory(starNftComposeCategory));
    }

    /**
     * 删除合成素材
     */
    @PreAuthorize("@ss.hasPermi('business:composecategory:remove')")
    @Log(title = "合成素材", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftComposeCategoryService.deleteStarNftComposeCategoryByIds(ids));
    }
}
