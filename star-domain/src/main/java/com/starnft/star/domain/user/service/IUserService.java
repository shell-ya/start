package com.starnft.star.domain.user.service;


import com.starnft.star.domain.user.model.dto.*;
import com.starnft.star.domain.user.model.res.PriorityTimesRes;
import com.starnft.star.domain.user.model.vo.*;

import java.util.List;

/**
 * @author WeiChunLAI
 */
public interface IUserService {

    /**
     * 账号密码登录
     *
     * @param req
     * @return
     */
    UserInfoVO login(UserLoginDTO req);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    UserInfoVO queryUserInfo(Long userId);

    UserInfo queryUserByMobile(String mobile);

    /**
     * 手机验证码登录/注册
     *
     * @param req
     * @return
     */
    UserRegisterInfoVO loginByPhone(UserLoginDTO req);

    /**
     * 用户登出
     *
     * @param userId
     * @return
     */
    Boolean logOut(Long userId);

    /**
     * 获取短信验证码
     *
     * @param req
     * @return
     */
    UserVerifyCode getVerifyCode(UserVerifyCodeDTO req);

    /**
     * 校验短信验证码
     *
     * @param req
     * @return
     */
    String verifyCode(UserVerifyCodeDTO req);

    /**
     * 初始化登录密码
     *
     * @return
     */
    Boolean setUpPassword(SetupPasswordDTO setupPasswordDTO);

    /**
     * 修改账户密码
     *
     * @param materialDTO
     * @return
     */
    Boolean changePassword(AuthMaterialDTO materialDTO);

    /**
     * 初始化支付密码
     *
     * @param req
     * @return
     */
    Boolean intiPayPassword(PayPasswordDTO req);

    /**
     * 修改支付密码
     *
     * @param req
     * @return
     */
    Boolean changePayPassword(PayPasswordDTO req);

    /**
     * 校验支付密码是否正确
     *
     * @param req
     * @return
     */
    String checkPayPassword(CheckPayPassword req);

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
     * 查询协议信息
     *
     * @param agreementId
     * @return
     */
    AgreementVO queryAgreementContentById(String agreementId);


    /**
     * 根据协议类型查询最新协议
     *
     * @param agreementType
     * @return
     */
    AgreementVO queryAgreementContentByType(Integer agreementType);

    /**
     * 保存用户同意协议的版本信息
     *
     * @param agreementIdDTO
     * @return
     */
    Boolean saveUserAgreementHistoryByUserId(AgreementIdDTO agreementIdDTO);

    /**
     * 根据协议id查询协议信息
     *
     * @param agreementIdList
     * @return
     */
    List<AgreementVO> queryAgreementByAgreementId(List<String> agreementIdList);

    /**
     * 插入协议的授权信息
     *
     * @param list
     * @param userId
     * @param authorizationId
     */
    void batchInsertAgreementSign(List<AgreementSignDTO> list, Long userId, Long authorizationId);

    /**
     * 获取用户真实信息
     *
     * @param uid
     * @return
     */
    UserRealInfo getUserRealInfo(Long uid);

    /**
     * 修改用户信息
     *
     * @param userInfoUpd
     * @return
     */
    Boolean modifyUserInfo(UserInfoUpdateDTO userInfoUpd);

    /**
     * 支付密码校验成功判断
     *
     * @param uid   用户id
     * @param token 支付密码校验时返回的凭证
     */
    void assertPayPwdCheckSuccess(Long uid, String token);

    void assertPayPwdCheckSuccess(Long uid, String token, boolean clear);

    void clearPayPwdCheckSuccessToken(Long uid);

    Boolean resetPassword(AuthMaterialDTO authMaterial);

    Boolean isSettingPayPassword(Long id);

    //修改 支付密码
    Boolean plyPasswordSetting(PayPasswordDTO payPasswordDTO);

    Boolean isCertification(Long userId);

    Long queryHasParent(Long userId);

    Boolean isWhite(Long uid, Integer whiteType);

    WhiteListConfigVO obtainWhiteConfig(Long goodsId);

    PriorityTimesRes queryPriorityTimes(Long uid, Long goodsId);

    Boolean whiteTimeConsume(Long uid, Long whiteId);
}
