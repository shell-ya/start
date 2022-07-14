package com.starnft.star.admin.web.controller.business;

import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.admin.web.controller.support.key.ITempKeyObtain;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Date 2022/6/22 10:24 AM
 * @Author ： shellya
 */
@RestController
@RequestMapping("/business/keys")
public class TempKeyController extends BaseController {

    @Resource
    private ITempKeyObtain iTempKeyObtain;

//    @ApiOperation("cos临时秘钥 default桶")
//    @PostMapping("/tempdefault")
//    public AjaxResult tempDefault() {
//        return AjaxResult.success(iTempKeyObtain.obtainTempKeyDefaultBucket(UserContext.getUserId().getUserId()));
//    }

    @ApiOperation("cos临时秘钥")
    @PostMapping("/temp")
    @PreAuthorize("@ss.hasPermi('business:keys:temp')")
    public AjaxResult temp(@RequestParam String bucketPrefix) {
        return AjaxResult.success(iTempKeyObtain.obtainTempKey(bucketPrefix,getUserId()));
    }
}
