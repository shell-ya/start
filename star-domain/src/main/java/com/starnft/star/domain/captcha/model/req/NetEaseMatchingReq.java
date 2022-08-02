package com.starnft.star.domain.captcha.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Harlan
 * @date 2022/07/27 17:17
 */
@Data
@ApiModel("网易验证码校验请求参数")
public class NetEaseMatchingReq implements Serializable {

    @ApiModelProperty("验证参数 NECaptchaValidate")
    @NotBlank(message = "验证参数不能为空")
    private String validate;
}
