package com.starnft.star.domain.user.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author WeiChunLAI
 */
@Data
public class UserVerifyCodeDTO {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码场景： 1=手机验证码登录 2=修改密码
     */
    private Integer verificationScenes;
}
