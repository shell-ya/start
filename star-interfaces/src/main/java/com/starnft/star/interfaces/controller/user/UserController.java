package com.starnft.star.interfaces.controller.user;

import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.application.process.user.req.*;
import com.starnft.star.application.process.user.res.*;
import com.starnft.star.common.RopResponse;
import com.starnft.star.domain.user.model.dto.AuthenticationNameDTO;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import com.starnft.star.interfaces.interceptor.UserResolverInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author WeiChunLAI
 * @date 2022/5/13 10:49
 */
@RestController
@RequestMapping("/user")
@Api(value = "UserController", tags = "用户管理")
public class UserController {

    @Autowired
    private UserCore userCore;


    @ApiOperation("短信验证码登录/注册")
    @PostMapping("/userinfo/loginbyphone")
    @TokenIgnore
    public RopResponse<UserInfoRes> loginByPhone(@Validated @RequestBody UserLoginReq req) {
        return RopResponse.success(this.userCore.loginByPhoneAndRegister(req));
    }

    @ApiOperation("账号密码登录")
    @PostMapping("/userinfo/loginbypassword")
    @TokenIgnore
    public RopResponse<UserInfoRes> loginByPassword(@Validated @RequestBody UserLoginReq req) {
        return RopResponse.success(this.userCore.loginByPassword(req));
    }

    @ApiOperation("用户退出")
    @PostMapping("/userinfo/logout")
    public RopResponse<Boolean> logout(UserResolverInfo userResolverInfo) {
        return RopResponse.success(this.userCore.logOut(userResolverInfo.getUserId()));
    }

    @ApiOperation("获取验证码")
    @PostMapping("/userinfo/getverifycode")
    @TokenIgnore
    public RopResponse<UserVerifyCodeRes> getVerifyCode(@Validated @RequestBody UserVerifyCodeReq req) {
        return RopResponse.success(this.userCore.getVerifyCode(req));
    }

    @ApiOperation("设置初始密码/重置密码")
    @PostMapping("/userinfo/setuppwd")
    @TokenIgnore
    public RopResponse<Boolean> setUpPassword(@Validated @RequestBody AuthMaterialReq req) {
        return RopResponse.success(this.userCore.setUpPassword(req));
    }

    @ApiOperation("修改密码")
    @PostMapping("/userinfo/changepwd")
    public RopResponse<Boolean> changepwd(@Validated @RequestBody AuthMaterialReq req) {
        return RopResponse.success(this.userCore.changePassword(req));
    }

    @ApiOperation("查询用户信息")
    @PostMapping("/userinfo/queryuserinfo")
    public RopResponse<UserGatheringInfoRes> getUserInfo(UserResolverInfo userResolverInfo) {
        UserGatheringInfoRes userGatheringInfoRes = this.userCore.ObtainUserGatheringInfo(new UserGatheringInfoReq(userResolverInfo.getUserId()));
        return RopResponse.success(userGatheringInfoRes);
    }

    @ApiOperation("发起实名认证")
    @PostMapping("/userinfo/authentication")
    public RopResponse<Boolean> sponsorAuthentication(UserResolverInfo userResolverInfo,
                                                      @Validated @RequestBody AuthenticationNameDTO req) {
        return RopResponse.success(this.userCore.realNameAuthentication(userResolverInfo.getUserId(), req));
    }

    @ApiOperation("查询实名认证结果")
    @PostMapping("/userinfo/getauthentication")
    public RopResponse queryAuthentication(UserResolverInfo userResolverInfo) {
        return RopResponse.success(this.userCore.queryAuthentication(userResolverInfo.getUserId()));
    }

    @ApiOperation("查询最新的协议信息")
    @GetMapping("/userinfo/agreement/queryopenagreement")
    public RopResponse<AgreementRes> queryOpenAgreement(@RequestParam("agreementType") Integer agreementType) {
        return RopResponse.success(this.userCore.queryNewAgreement(agreementType));
    }

    @ApiOperation("判断用户是否弹协议窗口")
    @GetMapping("/userinfo/agreement/checkagreementpopup")
    public RopResponse<PopupAgreementRes> checkAgreementPopup(UserResolverInfo userResolverInfo,
                                                              @RequestParam("scene") Integer scene) {
        return RopResponse.success(this.userCore.checkAgreementPopup(userResolverInfo.getUserId(), scene));
    }

    @ApiOperation("保存用户同意协议的版本信息")
    @GetMapping("/userinfo/agreement/insertagreement")
    public RopResponse saveUserAgreementHistoryByUserId(UserResolverInfo userResolverInfo,
                                                        @RequestBody AgreementIdRequest request) {
        this.userCore.saveUserAgreementHistoryByUserId(request.getAgreementId(), userResolverInfo.getUserId());
        return RopResponse.successNoData();
    }

    @ApiOperation("根据协议id查询协议的信息")
    @GetMapping("/userinfo/agreement/querycontent")
    public RopResponse<AgreementRes> queryAgreementContent(@RequestParam("agreementId") String agreementId) {
        return RopResponse.success(this.userCore.queryAgreementContent(agreementId));
    }

    @ApiOperation("根据协议场景查询最新的协议和弹窗信息")
    @GetMapping("/userinfo/agreement/queryagreementinfo")
    public RopResponse<AgreementAndNoticeRes> queryAgreementAndNotice(@RequestParam("agreementScene") Integer agreementScene) {
        return RopResponse.success(this.userCore.queryAgreementAndNotice(agreementScene));
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/userinfo/upd")
    public RopResponse<Boolean> updateUserInfo(UserResolverInfo userResolverInfo, @RequestBody UserInfoUpdReq req) {
        return RopResponse.success(this.userCore.modifyUserInfo(userResolverInfo.getUserId(), req));
    }

}
