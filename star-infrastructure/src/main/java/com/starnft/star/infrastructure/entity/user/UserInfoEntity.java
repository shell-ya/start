package com.starnft.star.infrastructure.entity.user;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * 用户信息
 * @author WeiChunLAI
 */
@TableName( "account_user")
@Data
public class UserInfoEntity extends BaseEntity {

    @Id
    @TableField(value = "id")
    private Long id;

    @TableField( "account")
    private Long account;

    @TableField( "password")
    private String password;

    @TableField( "phone")
    private String phone;

    @TableField( "nick_name")
    private String nickName;

    @TableField( "avatar")
    private String avatar;

    @TableField( "ply_password")
    private String plyPassword;

    @TableField( "is_active")
    private Boolean isActive;

    @TableField( "is_auth")
    private Boolean isAuth;
}
