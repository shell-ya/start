package com.starnft.star.interfaces.controller.user;

import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.application.process.user.UserTotalInfoCompose;
import com.starnft.star.application.process.user.req.AuthMaterialReq;
import com.starnft.star.application.process.user.req.UserGatheringInfoReq;
import com.starnft.star.application.process.user.req.UserLoginReq;
import com.starnft.star.application.process.user.req.UserVerifyCodeReq;
import com.starnft.star.application.process.user.res.AgreementRes;
import com.starnft.star.application.process.user.res.UserGatheringInfoRes;
import com.starnft.star.application.process.user.res.UserInfoRes;
import com.starnft.star.application.process.user.res.UserVerifyCodeRes;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.user.model.dto.AuthenticationNameDTO;
import com.starnft.star.application.process.user.res.PopupAgreementRes;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author WeiChunLAI
 * @date 2022/5/13 10:49
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserCore userCore;

    @Resource
    private UserTotalInfoCompose userTotalInfoCompose;


    @ApiOperation("短信验证码登录/注册")
    @PostMapping("/userinfo/loginbyphone")
    public RopResponse<UserInfoRes> loginByPhone(@Validated @RequestBody UserLoginReq req) {
        return RopResponse.success(userCore.loginByPhoneAndRegister(req));
    }

    @ApiOperation("账号密码登录")
    @PostMapping("/userinfo/loginbypassword")
    public RopResponse<UserInfoRes> loginByPassword(@Validated @RequestBody UserLoginReq req) {
        return RopResponse.success(userCore.loginByPassword(req));
    }

    @ApiOperation("用户退出")
    @PostMapping("/userinfo/logout")
    public RopResponse<Boolean> logout(@RequestHeader(StarConstants.USER_ID) Long userId) {
        return RopResponse.success(userCore.logOut(userId));
    }

    @ApiOperation("获取验证码")
    @PostMapping("/userinfo/getverifycode")
    public RopResponse<UserVerifyCodeRes> getVerifyCode(@Validated @RequestBody UserVerifyCodeReq req) {
        return RopResponse.success(userCore.getVerifyCode(req));
    }

    @ApiOperation("设置初始密码")
    @PostMapping("/userinfo/setuppwd")
    public RopResponse<Boolean> setUpPassword(@Validated @RequestBody AuthMaterialReq req) {
        return RopResponse.success(userCore.setUpPassword(req));
    }

    @ApiOperation("修改密码")
    @PostMapping("/userinfo/changepwd")
    public RopResponse<Boolean> changepwd(@Validated @RequestBody AuthMaterialReq req) {
        return RopResponse.success(userCore.changePassword(req));
    }


    @ApiOperation("查询用户信息")
    @PostMapping("/userinfo/queryuserinfo")
    public RopResponse getUserInfo(@RequestHeader(StarConstants.USER_ID) Long userId) {
        UserGatheringInfoRes userGatheringInfoRes = userTotalInfoCompose.ObtainUserGatheringInfo(new UserGatheringInfoReq(userId));
        return RopResponse.success(userGatheringInfoRes);
    }

    @ApiOperation("发起实名认证")
    @PostMapping("/userinfo/authentication")
    public RopResponse<Boolean> sponsorAuthentication(@Validated @RequestBody AuthenticationNameDTO req) {
        return RopResponse.success(userCore.realNameAuthentication(req));
    }

    @ApiOperation("查询实名认证结果")
    @GetMapping("/userinfo/authentication")
    public RopResponse queryAuthentication(@RequestHeader(StarConstants.USER_ID) Long userId) {
        return RopResponse.success(userCore.queryAuthentication(userId));
    }

    @ApiOperation("查询最新的协议信息")
    @GetMapping("/userinfo/queryopenagreement")
    public RopResponse<AgreementRes> queryOpenAgreement(@RequestParam("agreementType") Integer agreementType){
        return RopResponse.success(userCore.queryNewAgreement(agreementType));
    }

    @ApiOperation("判断用户是否弹协议窗口")
    @GetMapping("/userinfo/checkagreementpopup")
    public RopResponse<PopupAgreementRes> checkAgreementPopup(@RequestHeader(StarConstants.USER_ID) Long userId ,
                                                              @RequestParam("scene") Integer scene){

        return RopResponse.successNoData();
    }



}
