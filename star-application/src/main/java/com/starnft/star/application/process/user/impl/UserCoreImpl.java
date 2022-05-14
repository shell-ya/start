package com.starnft.star.application.process.user.impl;

import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.application.process.user.req.AuthMaterialReq;
import com.starnft.star.application.process.user.req.UserLoginReq;
import com.starnft.star.application.process.user.req.UserVerifyCodeReq;
import com.starnft.star.application.process.user.res.AgreementRes;
import com.starnft.star.application.process.user.res.PopupAgreementRes;
import com.starnft.star.application.process.user.res.UserInfoRes;
import com.starnft.star.application.process.user.res.UserVerifyCodeRes;
import com.starnft.star.common.enums.AgreementSceneEnum;
import com.starnft.star.common.enums.AgreementTypeEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.user.model.dto.AuthMaterialDTO;
import com.starnft.star.domain.user.model.dto.AuthenticationNameDTO;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.model.dto.UserVerifyCodeDTO;
import com.starnft.star.domain.user.model.vo.*;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.domain.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Service
public class UserCoreImpl implements UserCore {

    @Autowired
    IUserService userService;
    @Autowired
    IUserRepository userRepository;

    @Override
    public UserInfoRes loginByPassword(@Valid UserLoginReq req) {
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

    @Override
    public AgreementRes queryNewAgreement(Integer agreementType) {
        AgreementRes agreementRes = new AgreementRes();
        if (AgreementTypeEnum.UNKNOWN.equals(AgreementTypeEnum.getCode(agreementType))){
            AgreementVO agreementInfo = userService.queryAgreementContentByType(agreementType);
            if (Objects.nonNull(agreementInfo)) {
                //先写死注册场景
                agreementRes.setAgreementScene(AgreementSceneEnum.getCode(agreementInfo.getAgreementScene()).getDesc());
                agreementRes.setAgreementType(AgreementTypeEnum.getCode(agreementInfo.getAgreementType()).getDesc());
                agreementRes.setAgreementContent(agreementInfo.getAgreementContent())
                        .setAgreementName(agreementInfo.getAgreementName())
                        .setAgreementVersion(StringUtils.join("V" , agreementInfo.getAgreementVersion()));
            }else {
                log.error("user method queryNewAgreement {}" , StarError.AGREEMENT_NOT_FUND.getErrorMessage());
                throw new StarException(StarError.AGREEMENT_NOT_FUND);
            }
        }else {
            log.error("user method queryNewAgreement {}" , StarError.AGREEMENT_TYPE_UNKNOWN.getErrorMessage());
            throw new StarException(StarError.AGREEMENT_TYPE_UNKNOWN) ;
        }

        return agreementRes;
    }

    @Override
    public PopupAgreementRes checkAgreementPopup(Long userId, Integer authorizationSceneId) {
        PopupAgreementRes popupAgreementRes = new PopupAgreementRes();
        //排查用户不存在的情况
        if (userRepository.doesUserExist(userId)) {
            //查询用户签署的协议
            List<String> signAgreementId = userRepository.queryUserSignAgreement(userId);
            //查询最新的协议id
            List<AgreementVO> agreementVOS = userRepository.queryNewAgreementByScene(authorizationSceneId);
            if (CollectionUtils.isNotEmpty(agreementVOS)) {
                //不为空就说明签署过协议,但不一定签署过最新协议
                if (CollectionUtils.isNotEmpty(signAgreementId)) {
                    //去除重复的协议id
                    signAgreementId = signAgreementId.stream().distinct().collect(Collectors.toList());
                    //比较最新协议id和用户已经签署的协议id
                    for (String str : signAgreementId) {
                        agreementVOS.removeIf(value -> value.getAgreementId().equals(str));
                    }
                    if (CollectionUtils.isNotEmpty(agreementVOS)) {
                        //不为空则需要弹窗

                    }
                }
            }
        }else {
            log.error("user method checkAgreementPopup {}" , StarError.USER_EXISTS.getErrorMessage());
            throw new StarException(StarError.USER_EXISTS);
        }


        return popupAgreementRes;
    }

    private void popupAgreement(PopupAgreementRes popupAgreementRes
            ,List<AgreementVO> agreementVOS
            ,Integer authorizationSceneId){
        List<PopupAgreementInfo> popupAgreementInfoList = new ArrayList<>();
        PopupInfo popupInfo;
        //userRepository
    }
}
