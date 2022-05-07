package com.starnft.star.application.process.user;

import com.starnft.star.application.process.user.req.UserLoginReq;
import com.starnft.star.application.process.user.res.UserInfoRes;

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
}
