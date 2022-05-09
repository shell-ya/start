package com.starnft.star.application.process.user.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author WeiChunLAI
 */
@Data
public class UserVerifyCodeRes {

    @ApiModelProperty("验证码")
    private String code;
}
