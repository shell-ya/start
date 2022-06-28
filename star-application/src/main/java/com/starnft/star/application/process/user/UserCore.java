package com.starnft.star.application.process.user;

import com.starnft.star.application.process.user.req.*;
import com.starnft.star.application.process.user.res.*;
import com.starnft.star.domain.user.model.dto.AuthenticationNameDTO;
import com.starnft.star.domain.user.model.vo.UserAuthenticationVO;
import com.starnft.star.domain.user.model.vo.UserPlyPasswordVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserCore {

    /**
     * 账号密码登录
     *
     * @param req
     * @return
     */
    UserInfoRes loginByPassword(UserLoginReq req);

    /**
     * 手机验证码登录和自动注册
     *
     * @param req
     * @return
     */
    UserInfoRes loginByPhoneAndRegister(UserLoginReq req);

    /**
     * 退出登录状态
     *
     * @param userId
     * @return
     */
    Boolean logOut(Long userId);

    /**
     * 发送短信验证码
     *
     * @param req
     * @return
     */
    UserVerifyCodeRes getVerifyCode(UserVerifyCodeReq req);

    /**
     * @param userVerifyCodeReq
     * @return
     */
    String verifyCode(UserVerifyCodeReq userVerifyCodeReq);

    /**
     * 设置密码
     *
     * @param req
     * @return
     */
    Boolean setUpPassword(Long uid, SetupPasswordReq req);

    /**
     * 修改密码
     *
     * @param req
     * @return
     */
    Boolean changePassword(AuthMaterialReq req);

    /**
     * 用户汇总信息查询 用户个人信息、钱包信息
     *
     * @param req 获取用户总信息请求
     * @return 获取用户总信息响应
     */
    UserGatheringInfoRes ObtainUserGatheringInfo(UserGatheringInfoReq req);

    /**
     * 发起实名认证
     *
     * @param req
     * @return
     */
    Boolean realNameAuthentication(Long userId, AuthenticationNameDTO req);

    /**
     * 查询实名认证结果
     *
     * @param userId
     * @return
     */
    UserAuthenticationVO queryAuthentication(Long userId);

    /**
     * 根据协议类型查询最新协议内容
     *
     * @param agreementType
     * @return
     */
    AgreementRes queryNewAgreement(Integer agreementType);

    /**
     * 判断用户是否弹窗
     *
     * @param userId
     * @param authorizationSceneId
     * @return
     */
    PopupAgreementRes checkAgreementPopup(Long userId, Integer authorizationSceneId);

    /**
     * 保存用户签署协议历史信息
     *
     * @param agreementIds
     * @param userId
     */
    void saveUserAgreementHistoryByUserId(List<String> agreementIds, Long userId);

    /**
     * 查询协议的信息
     *
     * @param agreementId
     * @return
     */
    AgreementRes queryAgreementContent(String agreementId);

    /**
     * 根据协议场景查询协议信息
     *
     * @param agreementScene
     * @return
     */
    AgreementAndNoticeRes queryAgreementAndNotice(@RequestParam("agreementScene") Integer agreementScene);

    Boolean modifyUserInfo(Long uid, UserInfoUpdReq req);

    PayPwdPreCheckRes checkPayPassword(Long uid, PayPwdCheckReq req);

    Boolean resetPassword(AuthMaterialReq req);

    Boolean isSettingPayPassword(UserGatheringInfoReq userGatheringInfoReq);

    Boolean resetPayPassword(ResetPayPwdReq req);

    //修改 支付密码
    Boolean plyPasswordSetting(UserPlyPasswordVO userPlyPasswordVO);

    Boolean isCertification(Long userId);

    String shareCodeInfo(Long userId);
}
