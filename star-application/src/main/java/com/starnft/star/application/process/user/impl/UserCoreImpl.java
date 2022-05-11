package com.starnft.star.application.process.user.impl;

import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.application.process.user.req.AuthMaterialReq;
import com.starnft.star.application.process.user.req.UserLoginReq;
import com.starnft.star.application.process.user.req.UserVerifyCodeReq;
import com.starnft.star.application.process.user.res.UserInfoRes;
import com.starnft.star.application.process.user.res.UserVerifyCodeRes;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.user.model.dto.AuthMaterialDTO;
import com.starnft.star.domain.user.model.dto.AuthenticationNameDTO;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.model.dto.UserVerifyCodeDTO;
import com.starnft.star.domain.user.model.vo.UserAuthenticationVO;
import com.starnft.star.domain.user.model.vo.UserInfoVO;
import com.starnft.star.domain.user.model.vo.UserRegisterInfoVO;
import com.starnft.star.domain.user.model.vo.UserVerifyCode;
import com.starnft.star.domain.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional.ofNullable(req.getPhone())
                .orElseThrow(() -> new  StarException(StarError.PARAETER_UNSUPPORTED , "phone 不能为空"));
        Optional.ofNullable(req.getPassword())
                .orElseThrow(() -> new  StarException(StarError.PARAETER_UNSUPPORTED , "password 不能为空"));
        Optional.ofNullable(req.getLoginScenes())
                .orElseThrow(() -> new  StarException(StarError.PARAETER_UNSUPPORTED , "loginScenes 不能为空"));

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

    @Override
    public Boolean logOut(Long userId) {
        return userService.logOut(userId);
    }

    @Override
    public UserVerifyCodeRes getVerifyCode(UserVerifyCodeReq req) {
        UserVerifyCodeDTO userVerifyCodeDTO = BeanColverUtil.colver(req, UserVerifyCodeDTO.class);
        UserVerifyCode verifyCode = userService.getVerifyCode(userVerifyCodeDTO);
        return BeanColverUtil.colver(verifyCode , UserVerifyCodeRes.class);
    }

    @Override
    public Boolean setUpPassword(AuthMaterialReq req) {
        //必填参数校验
        Optional.ofNullable(req)
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED , "authMaterialReq 不能为空"));
        Optional.ofNullable(req.getPassword())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED , "password 不能为空"));
        Optional.ofNullable(req.getPhone())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED , "phone 不能为空"));
        Optional.ofNullable(req.getVerificationCode())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED , "verificationCode 不能为空"));

        AuthMaterialDTO authMaterial = BeanColverUtil.colver(req, AuthMaterialDTO.class);
        return userService.setUpPassword(authMaterial);
    }

    @Override
    public Boolean changePassword(AuthMaterialReq req) {
        Optional.ofNullable(req)
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED , "authMaterialReq 不能为空"));
        Optional.ofNullable(req.getPassword())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED , "password 不能为空"));
        Optional.ofNullable(req.getPhone())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED , "phone 不能为空"));
        Optional.ofNullable(req.getVerificationScenes())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED , "verificationScenes 不能为空"));
        Optional.ofNullable(req.getVerificationCode())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED , "verificationCode 不能为空"));
        Optional.ofNullable(req.getOldPassword())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED , "oldPassword 不能为空"));


        AuthMaterialDTO authMaterialreq = BeanColverUtil.colver(req, AuthMaterialDTO.class);
        return userService.changePassword(authMaterialreq);
    }

    @Override
    public Boolean realNameAuthentication(AuthenticationNameDTO req) {
        return userService.realNameAuthentication(req);
    }

    @Override
    public UserAuthenticationVO queryAuthentication(Long userId) {
        return userService.queryAuthentication(userId);
    }
}
