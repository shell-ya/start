package com.starnft.star.interfaces.controller.user;

import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.application.process.user.UserTotalInfoCompose;
import com.starnft.star.application.process.user.req.*;
import com.starnft.star.application.process.user.res.*;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.user.model.dto.AuthenticationNameDTO;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
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
@Api(value = "UserController", tags = "用户管理")
public class UserController {

    @Autowired
    private UserCore userCore;

    @Resource
    private UserTotalInfoCompose userTotalInfoCompose;


    @ApiOperation("短信验证码登录/注册")
    @PostMapping("/userinfo/loginbyphone")
    @TokenIgnore
    public RopResponse<UserInfoRes> loginByPhone(@Validated @RequestBody UserLoginReq req) {
        return RopResponse.success(userCore.loginByPhoneAndRegister(req));
    }

    @ApiOperation("账号密码登录")
    @PostMapping("/userinfo/loginbypassword")
    @TokenIgnore
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
    @TokenIgnore
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
    @GetMapping("/userinfo/agreement/queryopenagreement")
    public RopResponse<AgreementRes> queryOpenAgreement(@RequestParam("agreementType") Integer agreementType){
        return RopResponse.success(userCore.queryNewAgreement(agreementType));
    }

    @ApiOperation("判断用户是否弹协议窗口")
    @GetMapping("/userinfo/agreement/checkagreementpopup")
    public RopResponse<PopupAgreementRes> checkAgreementPopup(@RequestHeader(StarConstants.USER_ID) Long userId ,
                                                              @RequestParam("scene") Integer scene){
        return RopResponse.success(userCore.checkAgreementPopup(userId , scene));
    }

    @ApiOperation("保存用户同意协议的版本信息")
    @GetMapping("/userinfo/agreement/insertagreement")
    public RopResponse saveUserAgreementHistoryByUserId(@RequestHeader(StarConstants.USER_ID) Long userId ,
                                                        @RequestBody AgreementIdRequest request){
        userCore.saveUserAgreementHistoryByUserId(request.getAgreementId(),userId);
        return RopResponse.successNoData();
    }

    @ApiOperation("根据协议id查询协议的信息")
    @GetMapping("/userinfo/agreement/querycontent")
    public RopResponse<AgreementRes> queryAgreementContent(@RequestParam("agreementId") String agreementId){
        return RopResponse.success(userCore.queryAgreementContent(agreementId));
    }

    @ApiOperation("根据协议场景查询最新的协议和弹窗信息")
    @GetMapping("/userinfo/agreement/queryagreementinfo")
    public RopResponse<AgreementAndNoticeRes> queryAgreementAndNotice(@RequestParam("agreementScene") Integer agreementScene){
        return RopResponse.success(userCore.queryAgreementAndNotice(agreementScene));
    }




}
