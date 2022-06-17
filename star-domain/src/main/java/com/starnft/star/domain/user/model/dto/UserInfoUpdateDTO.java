package com.starnft.star.domain.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoUpdateDTO {

    /**
     * 账号
     */
    private Long account;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 支付密码
     */
    private String oldPlyPassword;

    /**
     * 支付密码
     */
    private String plyPassword;

    /**
     * 是否禁用
     */
    private Byte isActive;

    /**
     * 真实名称
     */
    private String fullName;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 是否实名认证
     */
    private Integer realPersonFlag;

    /**
     * 简介
     */
    private String briefIntroduction;
}
