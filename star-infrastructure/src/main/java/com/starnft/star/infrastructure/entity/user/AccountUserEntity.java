package com.starnft.star.infrastructure.entity.user;

import java.io.Serializable;

import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class AccountUserEntity extends BaseEntity implements Serializable {
    private Long id;

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
    private Byte realPersonFlag;

    private static final long serialVersionUID = 1L;
}