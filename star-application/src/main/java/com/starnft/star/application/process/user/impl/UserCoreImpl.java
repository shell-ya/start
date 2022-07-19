package com.starnft.star.application.process.user.impl;

import cn.hutool.core.lang.Validator;
import com.starnft.star.application.mq.producer.activity.ActivityEventProducer;
import com.starnft.star.application.process.event.model.EventReqAssembly;
import com.starnft.star.application.process.event.model.RegisterEventReq;
import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.application.process.user.req.*;
import com.starnft.star.application.process.user.res.*;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.enums.AgreementSceneEnum;
import com.starnft.star.common.enums.AgreementTypeEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.common.utils.InvitationCodeUtil;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.domain.event.model.res.EventActivityRes;
import com.starnft.star.domain.event.model.service.IEventActivityService;
import com.starnft.star.domain.scope.model.res.UserScopeRes;
import com.starnft.star.domain.scope.service.IUserScopeService;
import com.starnft.star.domain.support.key.model.DictionaryVO;
import com.starnft.star.domain.support.key.repo.IDictionaryRepository;
import com.starnft.star.domain.user.model.dto.*;
import com.starnft.star.domain.user.model.vo.*;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.domain.user.service.IUserService;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.res.CardBindResult;
import com.starnft.star.domain.wallet.model.res.WalletResult;
import com.starnft.star.domain.wallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
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
    @Resource
    private IUserScopeService userScopeService;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    RedisTemplate redisTemplate;
    @Resource
    ActivityEventProducer activityEventProducer;
    @Resource
    IEventActivityService eventActivityService;
    @Resource
    IDictionaryRepository dictionaryRepository;

    @Override
    public UserInfoRes loginByPassword(@Valid UserLoginReq req) {
        Optional.ofNullable(req.getPhone())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "phone 不能为空"));
        Optional.ofNullable(req.getPassword())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "password 不能为空"));
        Optional.ofNullable(req.getLoginScenes())
                .orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "loginScenes 不能为空"));

        UserLoginDTO userLoginDTO = BeanColverUtil.colver(req, UserLoginDTO.class);
        UserInfoVO userInfo = this.userService.login(userLoginDTO);
        return BeanColverUtil.colver(userInfo, UserInfoRes.class);
    }

    @Override
    public UserInfoRes loginByPhoneAndRegister(UserLoginReq req) {
        UserLoginDTO userLoginDTO = BeanColverUtil.colver(req, UserLoginDTO.class);
        UserRegisterInfoVO userInfo = this.userService.loginByPhone(userLoginDTO);
        activitySync(req, userInfo);  //判断是否邀请码进入
        return BeanColverUtil.colver(userInfo, UserInfoRes.class);
    }

    private void activitySync(UserLoginReq req, UserRegisterInfoVO userInfo) {
//        if (StringUtils.isBlank(req.getSc()) || StringUtils.isBlank(req.getAt())) {
//            return;
//        }
        if (StringUtils.isBlank(req.getSc())) {
            return;
        }
        Boolean isRegister = redisTemplate.hasKey(String.format(RedisKey.REDIS_USER_REG_NEW.getKey(), userInfo.getUserId()));
        if (isRegister) {
            try {
                RegisterEventReq rankEventReq = new RegisterEventReq();
                rankEventReq.setUserId(userInfo.getUserId());
                rankEventReq.setParent(InvitationCodeUtil.decode(req.getSc())); //邀请码转化为id //直接获取到了上级
                rankEventReq.setReqTime(new Date());
                rankEventReq.setEventSign(StarConstants.EventSign.Register.getSign());
                rankEventReq.setActivitySign(StringUtils.isBlank(req.getAt()) ? "la":req.getAt());
                activityEventProducer.sendScopeMessage(EventReqAssembly.assembly(rankEventReq));

            } catch (Exception e) {

            } finally {
                redisTemplate.delete(String.format(RedisKey.REDIS_USER_REG_NEW.getKey(), userInfo.getUserId()));
            }
        }
    }

    @Override
    public Boolean logOut(Long userId) {
        return this.userService.logOut(userId);
    }

    @Override
    public UserVerifyCodeRes getVerifyCode(UserVerifyCodeReq req) {
        UserVerifyCodeDTO userVerifyCodeDTO = BeanColverUtil.colver(req, UserVerifyCodeDTO.class);
        if (req.getVerificationScenes().equals(1)) {
            Boolean register = isRegister(req.getPhone());
            if (register) {
                throw new StarException(StarError.USER_EXISTS);
            }
        }
        UserVerifyCode verifyCode = this.userService.getVerifyCode(userVerifyCodeDTO);
        return BeanColverUtil.colver(verifyCode, UserVerifyCodeRes.class);
    }

    @Override
    public String verifyCode(UserVerifyCodeReq req) {
        UserVerifyCodeDTO userVerifyCodeDTO = BeanColverUtil.colver(req, UserVerifyCodeDTO.class);
        return this.userService.verifyCode(userVerifyCodeDTO);
    }

    @Override
    public Boolean setUpPassword(Long uid, SetupPasswordReq req) {
        //必填参数校验
        Assert.notNull(req, () -> new StarException(StarError.PARAETER_UNSUPPORTED, "参数不能为空"));
        Assert.notBlank(req.getPassword(), () -> new StarException(StarError.PARAETER_UNSUPPORTED, "密码不能为空"));
        return this.userService.setUpPassword(
                SetupPasswordDTO.builder()
                        .uid(uid)
                        .password(req.getPassword())
                        .build());
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
        return this.userService.changePassword(authMaterialreq);
    }

    @Override
    public UserGatheringInfoRes ObtainUserGatheringInfo(UserGatheringInfoReq req) {

        UserGatheringInfoRes userGatheringInfoRes = new UserGatheringInfoRes();

        //获取User信息
        UserInfoVO userInfoVO = this.userService.queryUserInfo(req.getUid());
        userInfoVO.setUserId(req.getUid());
        this.populateUserInfo(userGatheringInfoRes, userInfoVO);

        //获取钱包信息
        WalletResult walletResult = this.walletService.queryWalletInfo(new WalletInfoReq(req.getUid()));
        this.populateWalletInfo(userGatheringInfoRes, walletResult);

        //获取收款银行卡
        List<CardBindResult> cards = this.walletService.obtainCards(req.getUid());
        this.populateCardsInfo(userGatheringInfoRes, cards);
        //获取用户积分信息
//        UserScopeRes userScopeRes = this.userScopeService.getUserScopeByUserId(new UserScopeReq(req.getUid()));
//        this.populateScopeInfo(userGatheringInfoRes, userScopeRes);
        return userGatheringInfoRes;
    }

    private void populateScopeInfo(UserGatheringInfoRes userGatheringInfoRes, UserScopeRes userScopeRes) {
        userGatheringInfoRes.setScope(userScopeRes.getScope());
    }


    private void populateWalletInfo(UserGatheringInfoRes userGatheringInfoRes, WalletResult walletResult) {
        userGatheringInfoRes.setBalance(walletResult.getBalance());
        userGatheringInfoRes.setWalletId(walletResult.getWalletId());
    }

    private void populateUserInfo(UserGatheringInfoRes userGatheringInfoRes, UserInfoVO userInfoVO) {
        userGatheringInfoRes.setNickName(userInfoVO.getNickName());
        userGatheringInfoRes.setUid(userInfoVO.getUserId());
        userGatheringInfoRes.setPhone(userInfoVO.getPhone());
        userGatheringInfoRes.setAvatar(userInfoVO.getAvatar());
        userGatheringInfoRes.setBlockchainAddress(userInfoVO.getBlockchainAddress());
        userGatheringInfoRes.setBriefIntroduction(userInfoVO.getBriefIntroduction());
        userGatheringInfoRes.setRealPersonFlag(userInfoVO.getRealPersonFlag());
        userGatheringInfoRes.setPayPasswordFlag(StringUtils.isNotEmpty(userInfoVO.getPlyPassword()));
    }

    private void populateCardsInfo(UserGatheringInfoRes userGatheringInfoRes, List<CardBindResult> cards) {
        for (CardBindResult card : cards) {
            if (1 == card.getIsDefault()) {
                userGatheringInfoRes.setCollectionAccount(card.getCardNo());
                return;
            }
        }
    }

    @Override
    public Boolean realNameAuthentication(Long userId, AuthenticationNameDTO req) {
        return this.userService.realNameAuthentication(userId, req);
    }

    @Override
    public UserAuthenticationVO queryAuthentication(Long userId) {
        return this.userService.queryAuthentication(userId);
    }

    @Override
    public AgreementRes queryNewAgreement(Integer agreementType) {
        AgreementRes agreementRes = new AgreementRes();
        if (!AgreementTypeEnum.UNKNOWN.equals(AgreementTypeEnum.getCode(agreementType))) {
            AgreementVO agreementInfo = this.userService.queryAgreementContentByType(agreementType);
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
        if (this.userRepository.doesUserExist(userId)) {
            //查询用户签署的协议
            List<String> signAgreementId = this.userRepository.queryUserSignAgreement(userId);
            //查询最新的协议id
            List<AgreementVO> agreementVOS = this.userRepository.queryNewAgreementByScene(authorizationSceneId);
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
                        this.popupAgreement(popupAgreementRes, agreementVOS, authorizationSceneId);
                    }
                } else {
                    //为空则需要弹窗
                    this.popupAgreement(popupAgreementRes, agreementVOS, authorizationSceneId);
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
            List<AgreementVO> agreementInfos = this.userService.queryAgreementByAgreementId(agreementIds);
            if (CollectionUtils.isNotEmpty(agreementInfos)) {
                List<AgreementSignDTO> agreementSigns = new ArrayList<>();
                AgreementSignDTO agreementSignDTO;
                Long authorizationId = SnowflakeWorker.generateId();
                for (AgreementVO agreementInfo : agreementInfos) {
                    agreementSignDTO = new AgreementSignDTO();
                    agreementSignDTO.setAgreementId(agreementInfo.getAgreementId());
                    agreementSignDTO.setAuthorizationId(authorizationId);
                    agreementSignDTO.setUserId(userId);
                    agreementSignDTO.setIsDeleted(Boolean.FALSE);
                    agreementSignDTO.setCreatedAt(new Date());
                    agreementSignDTO.setCreatedBy(userId);
                    agreementSignDTO.setModifiedAt(new Date());
                    agreementSignDTO.setModifiedBy(userId);
                    agreementSigns.add(agreementSignDTO);
                }
                this.userService.batchInsertAgreementSign(agreementSigns, userId, authorizationId);
            } else {
                log.error("user method saveUserAgreementHistoryByUserId {}", StarError.AGREEMENT_NOT_FUND);
                throw new StarException(StarError.AGREEMENT_NOT_FUND);
            }
        } else {
            log.error("user method saveUserAgreementHistoryByUserId {} ", StarError.AGREEMENT_ID_NULL);
            throw new StarException(StarError.AGREEMENT_ID_NULL);
        }
    }

    @Override
    public AgreementRes queryAgreementContent(String agreementId) {
        AgreementVO agreementVO = this.userService.queryAgreementContentById(agreementId);
        AgreementRes agreementRes = BeanColverUtil.colver(agreementVO, AgreementRes.class);
        agreementRes.setAgreementScene(AgreementSceneEnum.getCode(agreementVO.getAgreementScene()).getDesc());
        agreementRes.setAgreementType(AgreementTypeEnum.getCode(agreementVO.getAgreementType()).getDesc());
        return agreementRes;
    }

    @Override
    public AgreementAndNoticeRes queryAgreementAndNotice(Integer agreementScene) {
        if (AgreementSceneEnum.getCode(agreementScene) != AgreementSceneEnum.UNKNOWN) {
            AgreementAndNoticeRes agreementAndNoticeRes = new AgreementAndNoticeRes();
            List<AgreementDetailRes> agreementDetailResList = new ArrayList<>();
            AgreementNoticeInfoRes agreementNoticeInfoRes = new AgreementNoticeInfoRes();
            AgreementDetailRes agreementDetailRes;
            //根据协议场景查询最新且已经生效的协议
            List<AgreementVO> agreementVOS = this.userRepository.queryNewAgreementByScene(agreementScene);
            for (AgreementVO agreementVO : agreementVOS) {
                agreementDetailRes = BeanColverUtil.colver(agreementVO, AgreementDetailRes.class);
                //先写死注册场景
                agreementDetailRes.setAgreementScene(AgreementSceneEnum.getCode(agreementVO.getAgreementScene()).getDesc());
                agreementDetailRes.setAgreementType(AgreementTypeEnum.getCode(agreementVO.getAgreementType()).getDesc());
                agreementDetailResList.add(agreementDetailRes);
            }

            //根据协议场景查询弹窗信息
            AgreementPopupInfoVO agreementPopupInfoVO = this.userRepository.queryAgreementPopupByScene(agreementScene);

            if (Objects.isNull(agreementPopupInfoVO)) {
                return agreementAndNoticeRes;
            }
            agreementAndNoticeRes.setAgreementDetailResList(agreementDetailResList);
            agreementAndNoticeRes.setAgreementNoticeInfo(agreementNoticeInfoRes);
            agreementNoticeInfoRes.setNoticeTitle(agreementPopupInfoVO.getAgreementPopupTitle());
            agreementNoticeInfoRes.setNoticeContent(agreementPopupInfoVO.getAgreementPopupContent());
            return agreementAndNoticeRes;
        } else {
            log.error("user method queryAgreementAndNotice");
            throw new StarException(StarError.AGREEMENT_NOT_FUND);
        }
    }

    @Override
    public Boolean modifyUserInfo(Long uid, UserInfoUpdReq req) {
        return this.userService.modifyUserInfo(
                UserInfoUpdateDTO.builder()
                        .account(uid)
                        .nickName(req.getNickName())
                        .briefIntroduction(req.getBriefIntroduction())
                        .build());
    }

    @Override
    public PayPwdPreCheckRes checkPayPassword(Long uid, PayPwdCheckReq req) {
        return new PayPwdPreCheckRes(
                this.userService.checkPayPassword(CheckPayPassword
                        .builder()
                        .userId(uid)
                        .payPassword(req.getPayPassword())
                        .build()));
    }

    @Override
    public Boolean resetPassword(AuthMaterialReq req) {
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
        return this.userService.resetPassword(authMaterial);
    }

    @Override
    public Boolean isSettingPayPassword(UserGatheringInfoReq userGatheringInfoReq) {
        return this.userService.isSettingPayPassword(userGatheringInfoReq.getUid());
    }

    @Override
    public Boolean resetPayPassword(ResetPayPwdReq req) {
        PayPasswordDTO payPasswordDTO = BeanColverUtil.colver(req, PayPasswordDTO.class);
        return this.userService.changePayPassword(payPasswordDTO);
    }

    @Override
    public Boolean plyPasswordSetting(UserPlyPasswordVO req) {
        PayPasswordDTO payPasswordDTO = BeanColverUtil.colver(req, PayPasswordDTO.class);
        return this.userService.plyPasswordSetting(payPasswordDTO);
    }

    @Override
    public Boolean isCertification(Long userId) {
        return this.userService.isCertification(userId);
    }

    @Override
    public ShareCodeRes shareCodeInfo(Long userId) {
        EventActivityRes activityRes = eventActivityService.queryEnabledActivity();
        String shareCode = InvitationCodeUtil.gen(userId);
        List<DictionaryVO> url = dictionaryRepository.obtainDictionary("URL");

        return ShareCodeRes.builder()
                .sc(shareCode)
                .at(activityRes.getActivitySign())
                .url(url.get(0).getDictCode())
                .build();
    }

    @Override
    public Boolean isRegister(String phone) {
        boolean isMobile = Validator.isMobile(phone);
        if (!isMobile) {
            throw new StarException("请填写正确手机号码");
        }
        String PREFIX = phone.substring(0, 3);
        String SUFFIX = phone.substring(3);
        Boolean isExist = this.redisTemplate.opsForValue().getBit(RedisKey.REDIS_USER_IS_REGISTERED_PREFIX.getKey(), Long.parseLong(PREFIX)) && this.redisTemplate.opsForValue().getBit(RedisKey.REDIS_USER_IS_REGISTERED_SUFFIX.getKey(), Long.parseLong(SUFFIX));
        if (!isExist) {
            UserInfo userInfo = this.userService.queryUserByMobile(phone);
            if (Objects.nonNull(userInfo)){
                this.redisTemplate.opsForValue().setBit(RedisKey.REDIS_USER_IS_REGISTERED_PREFIX.getKey(), Long.parseLong(PREFIX),true) ;
                this.redisTemplate.opsForValue().setBit(RedisKey.REDIS_USER_IS_REGISTERED_SUFFIX.getKey(), Long.parseLong(SUFFIX),true);
                isExist=true;
            }
        }
        return isExist;
    }

    private void popupAgreement(PopupAgreementRes popupAgreementRes
            , List<AgreementVO> agreementVOS
            , Integer authorizationSceneId) {
        List<PopupAgreementInfo> popupAgreementInfoList = new ArrayList<>();
        PopupAgreementInfo popupInfo;
        AgreementPopupInfoVO agreementPopupInfoVO = this.userRepository.queryAgreementPopupByScene(authorizationSceneId);
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
