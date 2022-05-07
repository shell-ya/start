package com.starnft.star.infrastructure.entity.user;

import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户信息
 * @author WeiChunLAI
 */
@Table(name = "account_user")
@Data
public class UserInfoEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "account")
    private Long account;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "ply_password")
    private String plyPassword;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_auth")
    private Boolean isAuth;
}
