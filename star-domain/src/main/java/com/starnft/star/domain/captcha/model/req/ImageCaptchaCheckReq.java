package com.starnft.star.domain.captcha.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Harlan
 * @date 2022/05/31 8:04
 */
@Data
@ApiModel("验证码校验请求参数")
public class ImageCaptchaCheckReq implements Serializable {

    @ApiModelProperty("验证码生成器 暂时只有一种：1 可不填 默认1 需与生成时的类型保持一致")
    private Integer creator;

    @ApiModelProperty(value = "生成验证码返回的id")
    @NotBlank(message = "验证码id不能为空")
    private String id;

    @ApiModelProperty(value = "验证码轨迹")
    @NotNull(message = "验证码轨迹不能为空")
    private StarImageCaptchaTrack captchaTrack;

}
