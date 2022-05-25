package com.starnft.star.application.process.user.impl;

import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.application.process.user.req.AuthMaterialReq;
import com.starnft.star.application.process.user.req.UserGatheringInfoReq;
import com.starnft.star.application.process.user.req.UserLoginReq;
import com.starnft.star.application.process.user.req.UserVerifyCodeReq;
import com.starnft.star.application.process.user.res.*;
import com.starnft.star.common.enums.AgreementSceneEnum;
import com.starnft.star.common.enums.AgreementTypeEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.domain.user.model.dto.*;
import com.starnft.star.domain.user.model.vo.*;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.domain.user.service.IUserService;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.res.WalletResult;
import com.starnft.star.domain.wallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private WalletService walletService;

    @Autowired
    IUserRepository userRepository;

    @Override
    public UserInfoRes loginByPassword(@Valid UserLoginReq req) {
        Optional.ofNullable(req.getPhone())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "phone 不能为空"));
        Optional.ofNullable(req.getPassword())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "password 不能为空"));
        Optional.ofNullable(req.getLoginScenes())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "loginScenes 不能为空"));

        UserLoginDTO userLoginDTO = BeanColverUtil.colver(req, UserLoginDTO.class);
        UserInfoVO userInfo = userService.login(userLoginDTO);
        return BeanColverUtil.colver(userInfo, UserInfoRes.class);
    }

    @Override
    public UserInfoRes loginByPhoneAndRegister(UserLoginReq req) {
        UserLoginDTO userLoginDTO = BeanColverUtil.colver(req, UserLoginDTO.class);
        UserRegisterInfoVO userInfo = userService.loginByPhone(userLoginDTO);
        return BeanColverUtil.colver(userInfo, UserInfoRes.class);
    }

    @Override
    public Boolean logOut(Long userId) {
        return userService.logOut(userId);
    }

    @Override
    public UserVerifyCodeRes getVerifyCode(UserVerifyCodeReq req) {
        UserVerifyCodeDTO userVerifyCodeDTO = BeanColverUtil.colver(req, UserVerifyCodeDTO.class);
        UserVerifyCode verifyCode = userService.getVerifyCode(userVerifyCodeDTO);
        return BeanColverUtil.colver(verifyCode, UserVerifyCodeRes.class);
    }

    @Override
    public Boolean setUpPassword(AuthMaterialReq req) {
        //必填参数校验
        Optional.ofNullable(req)
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "authMaterialReq 不能为空"));
        Optional.ofNullable(req.getPassword())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "password 不能为空"));
        Optional.ofNullable(req.getPhone())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "phone 不能为空"));
        Optional.ofNullable(req.getVerificationCode())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "verificationCode 不能为空"));

        AuthMaterialDTO authMaterial = BeanColverUtil.colver(req, AuthMaterialDTO.class);
        return userService.setUpPassword(authMaterial);
    }

    @Override
    public Boolean changePassword(AuthMaterialReq req) {
        Optional.ofNullable(req)
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "authMaterialReq 不能为空"));
        Optional.ofNullable(req.getPassword())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "password 不能为空"));
        Optional.ofNullable(req.getPhone())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "phone 不能为空"));
        Optional.ofNullable(req.getVerificationScenes())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "verificationScenes 不能为空"));
        Optional.ofNullable(req.getVerificationCode())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "verificationCode 不能为空"));
        Optional.ofNullable(req.getOldPassword())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "oldPassword 不能为空"));


        AuthMaterialDTO authMaterialreq = BeanColverUtil.colver(req, AuthMaterialDTO.class);
        return userService.changePassword(authMaterialreq);
    }

    @Override
    public UserGatheringInfoRes ObtainUserGatheringInfo(UserGatheringInfoReq req) {

        UserGatheringInfoRes userGatheringInfoRes = new UserGatheringInfoRes();

        //获取User信息
        UserInfoVO userInfoVO = userService.queryUserInfo(req.getUid());
        userInfoVO.setUserId(req.getUid());
        populateUserInfo(userGatheringInfoRes, userInfoVO);

        //获取钱包信息
        WalletResult walletResult = walletService.queryWalletInfo(new WalletInfoReq(req.getUid()));
        populateWalletInfo(userGatheringInfoRes, walletResult);

        return userGatheringInfoRes;
    }


    private void populateWalletInfo(UserGatheringInfoRes userGatheringInfoRes, WalletResult walletResult) {
        userGatheringInfoRes.setBalance(walletResult.getBalance());
        userGatheringInfoRes.setWalletId(walletResult.getWalletId());
        userGatheringInfoRes.setFrozen(walletResult.isFrozen());
        userGatheringInfoRes.setFrozen_fee(walletResult.getFrozen_fee());
        userGatheringInfoRes.setWallet_income(walletResult.getWallet_income());
        userGatheringInfoRes.setWallet_outcome(walletResult.getWallet_outcome());
    }

    private void populateUserInfo(UserGatheringInfoRes userGatheringInfoRes, UserInfoVO userInfoVO) {
        userGatheringInfoRes.setNickName(userInfoVO.getNickName());
        userGatheringInfoRes.setUid(userInfoVO.getUserId());
        userGatheringInfoRes.setPhone(userInfoVO.getPhone());
        userGatheringInfoRes.setAvatar(userInfoVO.getAvatar());
    }

    @Override
    public Boolean realNameAuthentication(Long userId  , AuthenticationNameDTO req) {
        return userService.realNameAuthentication(userId,req);
    }

    @Override
    public UserAuthenticationVO queryAuthentication(Long userId) {
        return userService.queryAuthentication(userId);
    }

    @Override
    public AgreementRes queryNewAgreement(Integer agreementType) {
        AgreementRes agreementRes = new AgreementRes();
        if (!AgreementTypeEnum.UNKNOWN.equals(AgreementTypeEnum.getCode(agreementType))) {
            AgreementVO agreementInfo = userService.queryAgreementContentByType(agreementType);
            if (Objects.nonNull(agreementInfo)) {
                //先写死注册场景
                agreementRes.setAgreementScene(AgreementSceneEnum.getCode(agreementInfo.getAgreementScene()).getDesc());
                agreementRes.setAgreementType(AgreementTypeEnum.getCode(agreementInfo.getAgreementType()).getDesc());
                agreementRes.setAgreementContent(agreementInfo.getAgreementContent())
                        .setAgreementName(agreementInfo.getAgreementName())
                        .setAgreementVersion(StringUtils.join("V", agreementInfo.getAgreementVersion()));
            } else {
                log.error("user method queryNewAgreement {}", StarError.AGREEMENT_NOT_FUND.getErrorMessage());
                throw new StarException(StarError.AGREEMENT_NOT_FUND);
            }
        } else {
            log.error("user method queryNewAgreement {}", StarError.AGREEMENT_TYPE_UNKNOWN.getErrorMessage());
            throw new StarException(StarError.AGREEMENT_TYPE_UNKNOWN);
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
                        popupAgreement(popupAgreementRes, agreementVOS, authorizationSceneId);
                    }
                } else {
                    //为空则需要弹窗
                    popupAgreement(popupAgreementRes, agreementVOS, authorizationSceneId);
                }
            }
        } else {
            log.error("user method checkAgreementPopup {}", StarError.USER_EXISTS.getErrorMessage());
            throw new StarException(StarError.USER_EXISTS);
        }
        return popupAgreementRes;
    }

    @Override
    public void saveUserAgreementHistoryByUserId(List<String> agreementIds, Long userId) {
        if (CollectionUtils.isNotEmpty(agreementIds)) {
            //根据协议id查询协议信息
            List<AgreementVO> agreementInfos = userService.queryAgreementByAgreementId(agreementIds);
            if (CollectionUtils.isNotEmpty(agreementInfos)){
                List<AgreementSignDTO> agreementSigns = new ArrayList<>();
                AgreementSignDTO agreementSignDTO;
                Long authorizationId = SnowflakeWorker.generateId();
                for (AgreementVO agreementInfo : agreementInfos) {
                    agreementSignDTO = new   AgreementSignDTO();
                    agreementSignDTO.setAgreementId(agreementInfo.getAgreementId());
                    agreementSignDTO.setAuthorizationId(authorizationId);
                    agreementSignDTO.setUserId(userId);
                    agreementSigns.add(agreementSignDTO);
                }
                userService.batchInsertAgreementSign(agreementSigns , userId , authorizationId);
            }else {
                log.error("user method saveUserAgreementHistoryByUserId {}" , StarError.AGREEMENT_NOT_FUND);
                throw new StarException(StarError.AGREEMENT_NOT_FUND);
            }
        }else {
            log.error("user method saveUserAgreementHistoryByUserId {} ", StarError.AGREEMENT_ID_NULL);
            throw new StarException(StarError.AGREEMENT_ID_NULL);
        }
    }

    @Override
    public AgreementRes queryAgreementContent(String agreementId) {
        AgreementVO agreementVO = userService.queryAgreementContentById(agreementId);
        return BeanColverUtil.colver(agreementVO , AgreementRes.class);
    }

    @Override
    public AgreementAndNoticeRes queryAgreementAndNotice(Integer agreementScene) {
        if (AgreementSceneEnum.getCode(agreementScene) != AgreementSceneEnum.UNKNOWN){
            AgreementAndNoticeRes agreementAndNoticeRes = new AgreementAndNoticeRes();
            List<AgreementDetailRes> agreementDetailResList = new ArrayList<>();
            AgreementNoticeInfoRes agreementNoticeInfoRes = new AgreementNoticeInfoRes();
            AgreementDetailRes agreementDetailRes;
            //根据协议场景查询最新且已经生效的协议
            List<AgreementVO> agreementVOS = userRepository.queryNewAgreementByScene(agreementScene);
            for (AgreementVO agreementVO : agreementVOS) {
                agreementDetailRes = BeanColverUtil.colver(agreementAndNoticeRes, AgreementDetailRes.class);
                agreementDetailResList.add(agreementDetailRes);
            }

            //根据协议场景查询弹窗信息
            AgreementPopupInfoVO agreementPopupInfoVO = userRepository.queryAgreementPopupByScene(agreementScene);
            agreementNoticeInfoRes.setNoticeTitle(agreementPopupInfoVO.getAgreementPopupTitle());
            agreementNoticeInfoRes.setNoticeContent(agreementPopupInfoVO.getAgreementPopupContent());

            agreementAndNoticeRes.setAgreementDetailResList(agreementDetailResList);
            agreementAndNoticeRes.setAgreementNoticeInfo(agreementNoticeInfoRes);
            return agreementAndNoticeRes;
        } else {
            log.error("user method queryAgreementAndNotice");
            throw new StarException(StarError.AGREEMENT_NOT_FUND);
        }
    }

    private void popupAgreement(PopupAgreementRes popupAgreementRes
            , List<AgreementVO> agreementVOS
            , Integer authorizationSceneId) {
        List<PopupAgreementInfo> popupAgreementInfoList = new ArrayList<>();
        PopupAgreementInfo popupInfo;
        AgreementPopupInfoVO agreementPopupInfoVO = userRepository.queryAgreementPopupByScene(authorizationSceneId);
        //如果弹窗内容为空，则不弹
        if (Objects.nonNull(agreementPopupInfoVO)) {
            for (AgreementVO agreementVO : agreementVOS) {
                popupInfo = new PopupAgreementInfo();
                popupInfo.setAgreementId(agreementVO.getAgreementId());
                popupInfo.setAgreementName(agreementVO.getAgreementName());
                popupAgreementInfoList.add(popupInfo);
            }
        }
        popupAgreementRes.setPopupInfo(new PopupInfo().setPopupTitle(agreementPopupInfoVO.getAgreementPopupTitle())
                .setPopupContent(agreementPopupInfoVO.getAgreementPopupContent())
        ).setPopupAgreementInfoList(popupAgreementInfoList);
    }
}
