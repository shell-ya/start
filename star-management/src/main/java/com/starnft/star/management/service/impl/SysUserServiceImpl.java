//package com.starnft.star.management.service.impl;
//
//import cn.dev33.satoken.stp.StpUtil;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.starnft.star.common.exception.StarError;
//import com.starnft.star.common.exception.StarException;
//import com.starnft.star.infrastructure.entity.sysuser.SysUser;
//import com.starnft.star.infrastructure.mapper.sysuser.SysUserMapper;
//import com.starnft.star.management.constants.SessionConstants;
//import com.starnft.star.management.model.dto.SysUserDto;
//import com.starnft.star.management.model.req.SysUserLoginReq;
//import com.starnft.star.management.service.SysUserService;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
///**
// * @Date 2022/5/11 7:55 PM
// * @Author ： shellya
// */
//@Service
//public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {
//
//    @Override
//    public SysUser getSysUser(Long id) {
//        return this.getById(id);
//    }
//
//    @Override
//    public SysUserDto sysUserLogin(SysUserLoginReq req) {
//        // TODO: 2022/5/19 密码加密
//        SysUser sysUser = this.lambdaQuery()
//                .ge(SysUser::getUsername, req.getUsername())
//                .ge(SysUser::getPassword, req.getPassword())
//                .one();
//
//        StpUtil.login(sysUser.getId());
//        StpUtil.getSession().set(SessionConstants.USER_INFO, sysUser);
//        Optional.ofNullable(sysUser)
//                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED , "密码错误"));
//        return SysUserDto.builder()
//                .id(sysUser.getId())
//                .status(sysUser.getStatus())
//                .token(StpUtil.getTokenValue())
//                .build();
//
//    }
//
//}
