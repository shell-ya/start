package com.starnft.star.domain.captcha.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Harlan
 * @date 2022/05/30 22:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("验证码响应结果")
public class StarImageCaptchaVO implements Serializable {

    @ApiModelProperty("验证码id 后续校验时需用到")
    private String id;

    @ApiModelProperty("背景图片")
    private String bgImage;

    @ApiModelProperty("滑块图片")
    private String sliderImage;

    @ApiModelProperty("背景图片宽")
    private Integer bgImageWidth;

    @ApiModelProperty("背景图片高")
    private Integer bgImageHeight;

    @ApiModelProperty("滑块图片宽")
    private Integer sliderImageWidth;

    @ApiModelProperty("滑块图片高")
    private Integer sliderImageHeight;
}
