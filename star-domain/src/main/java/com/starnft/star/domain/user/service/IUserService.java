package com.starnft.star.domain.user.service;


import com.starnft.star.domain.user.model.dto.*;
import com.starnft.star.domain.user.model.vo.UserInfoVO;
import com.starnft.star.domain.user.model.vo.UserRegisterInfoVO;
import com.starnft.star.domain.user.model.vo.UserVerifyCode;

/**
 * @author WeiChunLAI
 */
public interface IUserService {

    /**
     * 账号密码登录
     * @param req
     * @return
     */
    UserInfoVO login(UserLoginDTO req);

    /**
     * 手机验证码登录/注册
     * @param req
     * @return
     */
    UserRegisterInfoVO loginByPhone(UserLoginDTO req);

    /**
     * 用户登出
     * @param userId
     * @return
     */
    Boolean logOut(Long userId);

    /**
     * 获取短信验证码
     * @param req
     * @return
     */
    UserVerifyCode getVerifyCode(UserVerifyCodeDTO req);

    /**
     * 初始化登录密码
     * @param materialDTO
     * @return
     */
    Boolean setUpPassword(AuthMaterialDTO materialDTO);

    /**
     * 修改账户密码
     * @param materialDTO
     * @return
     */
    Boolean changePassword(AuthMaterialDTO materialDTO);

    /**
     * 初始化支付密码
     * @param req
     * @return
     */
    Boolean intiPayPassword(PayPasswordDTO req);

    /**
     * 修改支付密码
     * @param req
     * @return
     */
    Boolean changePayPassword(PayPasswordDTO req);

    /**
     * 校验支付密码是否正确
     * @param req
     * @return
     */
    Boolean checkPayPassword(CheckPayPassword req);

    /**
     * 发起实名认证
     * @param req
     * @return
     */
    Boolean realNameAuthentication(AuthenticationNameDTO req);

    //查询实名认证结果


    //查询协议信息


    //注册页面查询最新协议


    //保存用户同意协议的版本信息
    
}
