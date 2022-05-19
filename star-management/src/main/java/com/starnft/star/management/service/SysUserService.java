package com.starnft.star.management.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.starnft.star.infrastructure.entity.sysuser.SysUser;
import com.starnft.star.management.model.dto.SysUserDto;
import com.starnft.star.management.model.req.SysUserLoginReq;

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
     * @param req
     * @return
     */
    SysUserDto sysUserLogin(SysUserLoginReq req);
}
