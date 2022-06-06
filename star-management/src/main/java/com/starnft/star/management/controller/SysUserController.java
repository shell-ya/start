//package com.starnft.star.management.controller;
//
//import cn.dev33.satoken.stp.SaTokenInfo;
//import cn.dev33.satoken.stp.StpUtil;
//import cn.dev33.satoken.util.SaResult;
//import com.starnft.star.common.RopResponse;
//import com.starnft.star.common.exception.StarError;
//import com.starnft.star.common.exception.StarException;
//import com.starnft.star.infrastructure.entity.sysuser.SysUser;
//import com.starnft.star.management.annotation.Pass;
//import com.starnft.star.management.constants.SessionConstants;
//import com.starnft.star.management.model.dto.SysUserDto;
//import com.starnft.star.management.model.req.SysUserLoginReq;
//import com.starnft.star.management.service.SysUserService;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.Optional;
//
///**
// * @Date 2022/5/10 8:07 PM
// * @Author ： shellya
// */
//@RestController
//@RequestMapping("/sysuser")
//public class SysUserController {
//
//    @Resource
//    private SysUserService sysUserService;
//
//
//    @PostMapping("doLogin")
//    @Pass
//    public RopResponse<SysUserDto> login(@RequestBody SysUserLoginReq req){
//
//        return RopResponse.success(sysUserService.sysUserLogin(req));
//    }
//    @GetMapping("isLogin")
//    public String isLogin() {
//        return "当前会话是否登录：" + StpUtil.isLogin();
//    }
//
//    @GetMapping("logout")
//    public SaResult logout() {
//        StpUtil.logout();
//        return SaResult.ok();
//    }
//
//    @GetMapping("tokenInfo")
//    public SaResult tokenInfo() {
//        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
//        return SaResult.data(tokenInfo);
//    }
//}
