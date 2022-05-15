package com.starnft.star.management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.infrastructure.entity.sysuser.SysUser;
import com.starnft.star.infrastructure.mapper.sysuser.SysUserMapper;
import com.starnft.star.management.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Date 2022/5/11 7:55 PM
 * @Author ： shellya
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {

    @Override
    public SysUser getSysUser(Long id) {
        return this.getById(id);
    }

    @Override
    public SysUser sysUserLogin(String username, String password) {
        SysUser sysUser = this.lambdaQuery()
                .ge(SysUser::getUsername, username)
                .ge(SysUser::getPassword, password)
                .one();

        Optional.ofNullable(sysUser)
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED , "密码错误"));

        return sysUser;
    }

}
