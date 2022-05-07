package com.starnft.star.infrastructure.controller.user;

import com.starnft.star.application.process.user.UserTotalInfoCompose;
import com.starnft.star.application.process.user.req.UserGatheringInfoReq;
import com.starnft.star.application.process.user.res.UserGatheringInfoRes;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.model.vo.UserInfoVO;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.domain.user.model.vo.UserRegisterInfoVO;
import com.starnft.star.domain.user.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户相关接口
 *
 * @author WeiChunLai
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @Resource
    private UserTotalInfoCompose userTotalInfoCompose;


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
        UserGatheringInfoRes userGatheringInfoRes = userTotalInfoCompose.ObtainUserGatheringInfo(new UserGatheringInfoReq(userId));
        return RopResponse.success(userGatheringInfoRes);
    }

}
