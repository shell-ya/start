package com.starnft.star.interfaces.controller.apk;

import com.starnft.star.common.RopResponse;
import com.starnft.star.domain.apk.IApkInfoObtain;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Date 2022/7/16 9:44 PM
 * @Author ： shellya
 */
@RestController
@Api(tags = "安装包相关接口「ApkController」")
@RequestMapping("/apk")
public class ApkController {

    @Resource
    IApkInfoObtain apkInfoObtain;

    @ApiOperation("下载安装包")
    @GetMapping("/downloadapk/{phoneModel}")
    @TokenIgnore
    public RopResponse<String> downloadApk(@ApiParam(name = "手机型号", value = "0:安卓 1:苹果")
                                           @PathVariable("phoneModel") Integer phoneModel) {
        return RopResponse.success(0 == phoneModel ? apkInfoObtain.getUrl(phoneModel) : "https://itunes.apple.com/cn/app/id1634042904?mt=8");
    }

}
