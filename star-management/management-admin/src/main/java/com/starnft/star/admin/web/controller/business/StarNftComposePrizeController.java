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
import com.starnft.star.business.domain.StarNftComposePrize;
import com.starnft.star.business.service.IStarNftComposePrizeService;
import com.starnft.star.common.utils.poi.ExcelUtil;
import com.starnft.star.common.core.page.TableDataInfo;

/**
 * 合成奖品Controller
 *
 * @author ruoyi
 * @date 2022-07-30
 */
@RestController
@RequestMapping("/business/prize")
public class StarNftComposePrizeController extends BaseController
{
    @Autowired
    private IStarNftComposePrizeService starNftComposePrizeService;

    /**
     * 查询合成奖品列表
     */
    @PreAuthorize("@ss.hasPermi('business:prize:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftComposePrize starNftComposePrize)
    {
        startPage();
        List<StarNftComposePrize> list = starNftComposePrizeService.selectStarNftComposePrizeList(starNftComposePrize);
        return getDataTable(list);
    }

    /**
     * 导出合成奖品列表
     */
    @PreAuthorize("@ss.hasPermi('business:prize:export')")
    @Log(title = "合成奖品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftComposePrize starNftComposePrize)
    {
        List<StarNftComposePrize> list = starNftComposePrizeService.selectStarNftComposePrizeList(starNftComposePrize);
        ExcelUtil<StarNftComposePrize> util = new ExcelUtil<StarNftComposePrize>(StarNftComposePrize.class);
        util.exportExcel(response, list, "合成奖品数据");
    }

    /**
     * 获取合成奖品详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:prize:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftComposePrizeService.selectStarNftComposePrizeById(id));
    }

    /**
     * 新增合成奖品
     */
    @PreAuthorize("@ss.hasPermi('business:prize:add')")
    @Log(title = "合成奖品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftComposePrize starNftComposePrize)
    {
        return toAjax(starNftComposePrizeService.insertStarNftComposePrize(starNftComposePrize));
    }

    /**
     * 修改合成奖品
     */
    @PreAuthorize("@ss.hasPermi('business:prize:edit')")
    @Log(title = "合成奖品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftComposePrize starNftComposePrize)
    {
        return toAjax(starNftComposePrizeService.updateStarNftComposePrize(starNftComposePrize));
    }

    /**
     * 删除合成奖品
     */
    @PreAuthorize("@ss.hasPermi('business:prize:remove')")
    @Log(title = "合成奖品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftComposePrizeService.deleteStarNftComposePrizeByIds(ids));
    }
}
