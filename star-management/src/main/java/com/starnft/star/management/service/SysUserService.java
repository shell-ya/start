package com.starnft.star.management.service;

import com.starnft.star.infrastructure.entity.sysuser.SysUser;

/**
 * @Date 2022/5/11 7:55 PM
 * @Author ： shellya
 */
public interface SysUserService {
    /**
     * 系统用户ID
     * @param id
     * @return
     */
    SysUser getSysUser(Long id);

    /**
     * 系统用户登录
     * @param username
     * @param password
     * @return
     */
    SysUser sysUserLogin(String username,String password);
}
