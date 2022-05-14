package com.starnft.star.application.process.user;

import com.starnft.star.application.process.user.req.AuthMaterialReq;
import com.starnft.star.application.process.user.req.UserLoginReq;
import com.starnft.star.application.process.user.req.UserVerifyCodeReq;
import com.starnft.star.application.process.user.res.AgreementRes;
import com.starnft.star.application.process.user.res.PopupAgreementRes;
import com.starnft.star.application.process.user.res.UserInfoRes;
import com.starnft.star.application.process.user.res.UserVerifyCodeRes;
import com.starnft.star.domain.user.model.dto.AuthenticationNameDTO;
import com.starnft.star.domain.user.model.vo.AgreementVO;
import com.starnft.star.domain.user.model.vo.UserAuthenticationVO;

public interface UserCore {

    /**
     * 账号密码登录
     * @param req
     * @return
     */
    UserInfoRes loginByPassword(UserLoginReq req);

    /**
     * 手机验证码登录和自动注册
     * @param req
     * @return
     */
    UserInfoRes loginByPhoneAndRegister(UserLoginReq req);

    /**
     * 退出登录状态
     * @param userId
     * @return
     */
    Boolean logOut(Long userId);

    /**
     * 发送短信验证码
     * @param req
     * @return
     */
    UserVerifyCodeRes getVerifyCode(UserVerifyCodeReq req);

    /**
     * 设置密码
     * @param req
     * @return
     */
    Boolean setUpPassword(AuthMaterialReq req);

    /**
     * 修改密码
     * @param req
     * @return
     */
    Boolean changePassword(AuthMaterialReq req);

    /**
     * 发起实名认证
     * @param req
     * @return
     */
    Boolean realNameAuthentication(AuthenticationNameDTO req);

    /**
     * 查询实名认证结果
     * @param userId
     * @return
     */
    UserAuthenticationVO queryAuthentication(Long userId);

    /**
     * 根据协议类型查询最新协议内容
     * @param agreementType
     * @return
     */
    AgreementRes queryNewAgreement(Integer agreementType);

    /**
     * 判断用户是否弹窗
     * @param userId
     * @param authorizationSceneId
     * @return
     */
    PopupAgreementRes checkAgreementPopup(Long userId, Integer authorizationSceneId);
}
