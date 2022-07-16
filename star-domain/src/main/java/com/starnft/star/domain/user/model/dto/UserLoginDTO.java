package com.starnft.star.domain.user.model.dto;

import lombok.Data;

/**
 * @author WeiChunLAI
 */
@Data
public class UserLoginDTO {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录类型(1-密码登录 2-短信验证码登录)
     */
    private Integer loginScenes;

    /**
     * 手机验证码
     */
    private String code;
    /**
     * 邀请码
     */

    private String sc;
     /**
     * 活动类型
     */
    private String at;
}
