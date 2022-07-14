package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.NftWallet;
import com.starnft.star.business.service.INftWalletService;
import com.starnft.star.common.annotation.Log;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 钱包Controller
 *
 * @author shellya
 * @date 2022-06-08
 */
@RestController
@RequestMapping("/business/wallet")
public class NftWalletController extends BaseController
{
    @Resource
    private INftWalletService nftWalletService;
    /**
     * 获取钱包详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:wallet:queryByUid')")
    @GetMapping(value = "/{uid}")
    public AjaxResult getInfo(@PathVariable("uid") Long uid)
    {
        return AjaxResult.success(nftWalletService.selectNftWalletByUid(uid));
    }

    /**
     * 修改钱包
     */
    @PreAuthorize("@ss.hasPermi('business:wallet:edit')")
    @Log(title = "钱包", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NftWallet nftWallet)
    {
        return toAjax(nftWalletService.updateNftWallet(nftWallet));
    }

}
