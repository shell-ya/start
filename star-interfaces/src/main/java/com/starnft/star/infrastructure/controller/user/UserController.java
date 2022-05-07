package com.starnft.star.infrastructure.controller.user;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.model.dto.UserLoginDTO;
import com.starnft.star.domain.model.vo.UserInfoVO;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.infrastructure.model.dto.PayPasswordDTO;
import com.starnft.star.infrastructure.model.dto.SmsCodeDTO;
import com.starnft.star.domain.model.vo.UserRegisterInfoVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关接口
 *
 * @author WeiChunLai
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserRepository userService;

    @ApiOperation("短信验证码登录/注册")
    @PostMapping("/userinfo/loginbyphone")
    public RopResponse<UserRegisterInfoVO> loginByPhone(@Validated @RequestBody UserLoginDTO req) {
        return RopResponse.success(userService.loginByPhone(req));
    }

    @ApiOperation("用户登录")
    @PostMapping("/userinfo/login")
    public RopResponse<UserInfoVO> login(@Validated @RequestBody UserLoginDTO req) {
        return RopResponse.success(userService.login(req));
    }

    @ApiOperation("用户退出")
    @PostMapping("/userinfo/logout")
    public RopResponse logout(@RequestHeader(StarConstants.USER_ID) Long userId) {
        return RopResponse.success(null);
    }

    @ApiOperation("查询用户信息")
    @PostMapping("/userinfo/queryuserinfo")
    public RopResponse getUserInfo(@RequestHeader(StarConstants.USER_ID) Long userId) {
        return RopResponse.success(null);
    }

    @ApiOperation("获取短信验证码")
    @PostMapping("/userinfo/querysmscode")
    public RopResponse getSMSCode(@Validated @RequestBody SmsCodeDTO req) {
        return RopResponse.successNoData();
    }

    @ApiOperation("设置支付密码")
    @PostMapping("/userinfo/setpaypassword")
    public RopResponse setPayPassword(@RequestHeader(StarConstants.USER_ID) Long userId, @RequestBody PayPasswordDTO req) {
        return RopResponse.successNoData();
    }

}
