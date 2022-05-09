package com.starnft.star.application.process.user;

import com.starnft.star.application.process.user.req.AuthMaterialReq;
import com.starnft.star.application.process.user.req.UserLoginReq;
import com.starnft.star.application.process.user.req.UserVerifyCodeReq;
import com.starnft.star.application.process.user.res.UserInfoRes;
import com.starnft.star.application.process.user.res.UserVerifyCodeRes;

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
}
