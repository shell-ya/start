package com.starnft.star.infrastructure.controller.user;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.StarConstants;
import com.starnft.star.infrastructure.model.dto.PayPasswordDTO;
import com.starnft.star.infrastructure.model.dto.SmsCodeDTO;
import com.starnft.star.infrastructure.model.dto.UserLoginDTO;
import com.starnft.star.infrastructure.model.dto.UserRegisterDTO;
import com.starnft.star.infrastructure.model.vo.UserInfoVO;
import com.starnft.star.infrastructure.model.vo.UserRegisterInfoVO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关接口
 * @author WeiChunLai
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("用户注册")
    @PostMapping("/userinfo/register")
    public RopResponse<UserRegisterInfoVO> register(@Validated @RequestBody UserRegisterDTO req){
        return RopResponse.success(new  UserRegisterInfoVO());
    }

    @ApiOperation("用户登录")
    @PostMapping("/userinfo/login")
    public RopResponse<UserInfoVO> login(@Validated @RequestBody UserLoginDTO req){
        return RopResponse.success(new  UserInfoVO());
    }

    @ApiOperation("用户退出")
    @PostMapping("/userinfo/logout")
    public RopResponse logout(@RequestHeader(StarConstants.USER_ID) Long userId){
        return RopResponse.success(null);
    }

    @ApiOperation("查询用户信息")
    @PostMapping("/userinfo/queryuserinfo")
    public RopResponse getUserInfo(@RequestHeader(StarConstants.USER_ID) Long userId){
        return RopResponse.success(null);
    }

    @ApiOperation("获取短信验证码")
    @PostMapping("/userinfo/querysmscode")
    public RopResponse getSMSCode(@Validated @RequestBody SmsCodeDTO req){
        return RopResponse.successNoData();
    }

    @ApiOperation("设置支付密码")
    @PostMapping("/userinfo/setpaypassword")
    public RopResponse setPayPassword(@RequestHeader(StarConstants.USER_ID) Long userId, @RequestBody PayPasswordDTO req){
        return  RopResponse.successNoData();
    }

}
