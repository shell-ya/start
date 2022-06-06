//package com.starnft.star.infrastructure.entity.sysuser;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import com.starnft.star.infrastructure.entity.BaseEntity;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Getter;
//
///**
// * @Date 2022/5/10 2:24 PM
// * @Author ： shellya
// */
//@Data
//@Builder
//@TableName("sys_user")
//public class SysUser extends BaseEntity {
//
//    @TableId(value = "id", type = IdType.AUTO)
//    private Long id;
//
//    @TableField("username")
//    private String username;
//
//    @TableField("password")
//    private String password;
//
//    @TableField("role")
//    private Integer role;
//
//    @TableField("`status`")
//    private Boolean status;
//    @TableField( "is_deleted")
//    private Boolean isDeleted;
//}
