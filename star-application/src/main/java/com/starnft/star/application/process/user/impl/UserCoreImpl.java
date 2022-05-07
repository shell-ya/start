package com.starnft.star.application.process.user.impl;

import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.application.process.user.req.UserLoginReq;
import com.starnft.star.application.process.user.res.UserInfoRes;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.model.vo.UserInfoVO;
import com.starnft.star.domain.user.model.vo.UserRegisterInfoVO;
import com.starnft.star.domain.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Service
public class UserCoreImpl implements UserCore {

    @Autowired
    IUserService userService;

    @Override
    public UserInfoRes loginByPassword(UserLoginReq req) {
        UserLoginDTO userLoginDTO = BeanColverUtil.colver(req, UserLoginDTO.class);
        UserInfoVO userInfo = userService.login(userLoginDTO);
        return BeanColverUtil.colver(userInfo , UserInfoRes.class);
    }

    @Override
    public UserInfoRes loginByPhoneAndRegister(UserLoginReq req) {
        UserLoginDTO userLoginDTO = BeanColverUtil.colver(req, UserLoginDTO.class);
        UserRegisterInfoVO userInfo = userService.loginByPhone(userLoginDTO);
        return BeanColverUtil.colver(userInfo , UserInfoRes.class);
    }
}
