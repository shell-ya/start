package com.starnft.star.interfaces.controller.user;

import com.starnft.star.common.RopResponse;
import com.starnft.star.domain.support.key.ITempKeyObtain;
import com.starnft.star.domain.support.key.model.TempKey;
import com.starnft.star.interfaces.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/keys")
public class TempKeyController {

    @Resource
    private ITempKeyObtain iTempKeyObtain;

    @ApiOperation("cos临时秘钥 default桶")
    @PostMapping("/tempdefault")
    public RopResponse<TempKey> tempDefault() {
        return RopResponse.success(iTempKeyObtain.obtainTempKeyDefaultBucket(UserContext.getUserId().getUserId()));
    }

    @ApiOperation("cos临时秘钥")
    @PostMapping("/temp")
    public RopResponse<TempKey> temp(@RequestParam String bucketPrefix) {
        return RopResponse.success(iTempKeyObtain.obtainTempKey(bucketPrefix, UserContext.getUserId().getUserId()));
    }
}
