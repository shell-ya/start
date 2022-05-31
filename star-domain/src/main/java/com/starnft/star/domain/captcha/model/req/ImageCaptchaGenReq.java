package com.starnft.star.domain.captcha.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Harlan
 * @date 2022/05/31 8:06
 */
@Data
@ApiModel("验证码生成请求参数")
public class ImageCaptchaGenReq implements Serializable {

    @ApiModelProperty("验证码生成器 暂时只有一种：1 可不填 默认1")
    private Integer creator;

    @ApiModelProperty("验证码类型 ：1=滑块 2=旋转 3=滑动还原 4=图像点选 5=单词点选 可不填 默认1")
    private Integer type;

}
