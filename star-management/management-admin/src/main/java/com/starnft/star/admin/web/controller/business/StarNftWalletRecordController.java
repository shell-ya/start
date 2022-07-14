package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarNftWalletRecord;
import com.starnft.star.business.domain.StarNftWithdrawApply;
import com.starnft.star.business.service.IStarNftWalletRecordService;
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
 * 钱包交易记录Controller
 *
 * @author shellya
 * @date 2022-06-09
 */
@RestController
@RequestMapping("/business/walletrecord")
public class StarNftWalletRecordController extends BaseController
{
    @Resource
    private IStarNftWalletRecordService starNftWalletRecordService;

    /**
     * 查询钱包交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('business:walletrecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftWalletRecord starNftWalletRecord)
    {
        startPage();
        List<StarNftWalletRecord> list = starNftWalletRecordService.selectStarNftWalletRecordList(starNftWalletRecord);
        return getDataTable(list);
    }

    /**
     * 导出钱包交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('business:walletrecord:export')")
    @Log(title = "钱包交易记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarNftWalletRecord starNftWalletRecord)
    {
        List<StarNftWalletRecord> list = starNftWalletRecordService.selectStarNftWalletRecordList(starNftWalletRecord);
        ExcelUtil<StarNftWalletRecord> util = new ExcelUtil<StarNftWalletRecord>(StarNftWalletRecord.class);
        util.exportExcel(response, list, "钱包交易记录数据");
    }

    /**
     * 获取钱包交易记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:walletrecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starNftWalletRecordService.selectStarNftWalletRecordById(id));
    }

    /**
     * 新增钱包交易记录
     */
    @PreAuthorize("@ss.hasPermi('business:walletrecord:add')")
    @Log(title = "钱包交易记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftWalletRecord starNftWalletRecord)
    {
        return toAjax(starNftWalletRecordService.insertStarNftWalletRecord(starNftWalletRecord));
    }

    /**
     * 修改钱包交易记录
     */
    @PreAuthorize("@ss.hasPermi('business:walletrecord:edit')")
    @Log(title = "钱包交易记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftWalletRecord starNftWalletRecord)
    {
        return toAjax(starNftWalletRecordService.updateStarNftWalletRecord(starNftWalletRecord));
    }

    /**
     * 删除钱包交易记录
     */
    @PreAuthorize("@ss.hasPermi('business:walletrecord:remove')")
    @Log(title = "钱包交易记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starNftWalletRecordService.deleteStarNftWalletRecordByIds(ids));
    }
    @PreAuthorize(("@ss.hasPermi('business:walletrecord:user')"))
    @GetMapping("/record")
    public TableDataInfo selectUserWalletRecord(StarNftWithdrawApply starNftWithdrawApply){
        startPage();
        List<StarNftWalletRecord> list = starNftWalletRecordService.selectUserRecord(starNftWithdrawApply.getWithdrawUid());
        return getDataTable(list);
    }
}
