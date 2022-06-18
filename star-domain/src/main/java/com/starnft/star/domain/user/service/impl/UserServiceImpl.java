package com.starnft.star.domain.user.service.impl;

import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.constant.YesOrNoStatusEnum;
import com.starnft.star.common.enums.LoginTypeEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.po.AccessToken;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.common.utils.StarUtils;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.sms.interfaces.MessageStrategyInterface;
import com.starnft.star.domain.user.model.dto.*;
import com.starnft.star.domain.user.model.vo.*;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.domain.user.service.IUserService;
import com.starnft.star.domain.user.service.strategy.UserLoginStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseUserService implements IUserService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IUserRepository userRepository;

    @Resource(name = "swMessageStrategy")
    private MessageStrategyInterface messageStrategyInterface;

    @Value("${star.sms.enable: false}")
    private Boolean smsEnable;

    @Override
    public UserInfoVO login(UserLoginDTO req) {

        LoginTypeEnum loginTypeEnum = LoginTypeEnum.getLoginTypeEnum(req.getLoginScenes());
        UserLoginStrategy login = this.applicationContext.getBean(loginTypeEnum.getStrategy(), UserLoginStrategy.class);
        Long userId = login.login(req);

        //创建token
        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(userId);

        UserInfoVO userInfo = new UserInfoVO();
        userInfo.setToken(this.createUserTokenAndSaveRedis(userId));
        userInfo.setUserId(userId);

        return userInfo;
    }

    @Override
    public UserInfoVO queryUserInfo(Long userId) {
        UserInfo userInfo = this.userRepository.queryUserInfoByUserId(userId);
        if (Objects.isNull(userInfo)) {
            throw new StarException(StarError.USER_NOT_EXISTS);
        }
        return UserInfoVO.builder()
                .userId(userInfo.getId())
                .phone(userInfo.getPhone())
                .nickName(userInfo.getNickName())
                .avatar(userInfo.getAvatar())
                .realPersonFlag(YesOrNoStatusEnum.YES.getCode().equals(userInfo.getRealPersonFlag()))
                .blockchainAddress(userInfo.getBlockchainAddress())
                .briefIntroduction(userInfo.getBriefIntroduction())
                .plyPassword(userInfo.getPlyPassword())
                .build();
    }

    @Override
    public UserRegisterInfoVO loginByPhone(UserLoginDTO req) {

        LoginTypeEnum loginTypeEnum = LoginTypeEnum.getLoginTypeEnum(req.getLoginScenes());
        UserLoginStrategy login = this.applicationContext.getBean(loginTypeEnum.getStrategy(), UserLoginStrategy.class);
        Long userId = login.login(req);

        UserRegisterInfoVO userRegisterInfoVO = new UserRegisterInfoVO();

        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(userId);
        userRegisterInfoVO.setToken(this.createUserTokenAndSaveRedis(userId));
        userRegisterInfoVO.setUserId(userId);

        return userRegisterInfoVO;
    }

    @Transactional
    @Override
    public Boolean logOut(Long userId) {
        this.userRepository.deleteLoginLog(userId);
        return this.cleanUserToken(userId);
    }

    @Override
    public UserVerifyCode getVerifyCode(UserVerifyCodeDTO req) {
        //发送验证码
        String code = StarUtils.getVerifyCode();

        //校验验证码发送的频率
        String key = String.format(RedisKey.SMS_CODE_LIFE.getKey(), req.getPhone());
        if (this.smsEnable) {
            Object obj = this.redisTemplate.opsForValue().get(key);
            if (!Objects.isNull(obj)) {
                throw new StarException(StarError.VERIFYCODE_FREQUENCY_IS_TOO_HIGH);
            }
            //调用服务商发送短信
            boolean isSend = this.messageStrategyInterface.checkCodeMessage(req.getPhone(), code);
            if (!isSend) {
                throw new StarException("短信发送失败");
            }

        }

        //录入redis
        RedisKey redisKeyEnum = RedisKey.getRedisKeyEnum(req.getVerificationScenes());
        String redisKey = String.format(redisKeyEnum.getKey(), req.getPhone());
        try {
            this.redisTemplate.opsForValue().set(redisKey, code, redisKeyEnum.getTime(), redisKeyEnum.getTimeUnit());
            //限制短信发送时间
            this.redisTemplate.opsForValue().set(key, req.getPhone(), RedisKey.SMS_CODE_LIFE.getTime(), RedisKey.SMS_CODE_LIFE.getTimeUnit());
        } catch (Exception e) {
            log.error("sms redis error:{}", e);
        }

        // 如发送短信则不返回验证码
        return this.smsEnable ? null : UserVerifyCode.builder().code(code).build();
    }

    @Override
    public Boolean verifyCode(UserVerifyCodeDTO req) {
        //录入redis
        RedisKey redisKeyEnum = RedisKey.getRedisKeyEnum(req.getVerificationScenes());
        if (Objects.isNull(redisKeyEnum)) {
            throw new StarException(StarError.PARAETER_UNSUPPORTED, "验证码场景不存在");
        }
        String redisKey = String.format(redisKeyEnum.getKey(), req.getPhone());
        Object obj = this.redisTemplate.opsForValue().get(redisKey);
        if (Objects.nonNull(obj)) {
            if (obj.toString().equals(req.getCode())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean setUpPassword(SetupPasswordDTO setupPasswordDTO) {
        //校验用户是否存在
        UserInfo userInfo = this.userRepository.queryUserInfoByUserId(setupPasswordDTO.getUid());
        if (Objects.isNull(userInfo)) {
            log.error("设置初始密码，用户：{}不存在", setupPasswordDTO.getUid());
            throw new StarException(StarError.USER_NOT_EXISTS);
        }

        //判断是否已设置过初登录密码
        Assert.isNull(userInfo.getPassword(), () -> new StarException(StarError.USER_SETUP_PASSWORD_ERROR));

        Integer updateRows = this.userRepository.setUpPassword(userInfo, setupPasswordDTO.getPassword());
        return Objects.nonNull(updateRows) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Boolean changePassword(AuthMaterialDTO materialDTO) {
        //校验用户是否存在
        UserInfo userInfo = this.userRepository.queryUserInfoByPhone(materialDTO.getPhone());
        if (Objects.isNull(userInfo)) {
            log.error("修改密码，用户：{} 不存在", materialDTO.getPhone());
            throw new StarException(StarError.USER_NOT_EXISTS);
        }

        //校验验证码是否过期
        String redisKey = String.format(RedisKey.REDIS_CODE_NOT_LOGIN_CHANGE_PWD.getKey(), materialDTO.getPhone());
        String code = String.valueOf(this.redisTemplate.opsForValue().get(redisKey));
        Optional.ofNullable(materialDTO)
                .filter(a -> a.getVerificationCode().equals(code))
                .orElseThrow(() -> new StarException(StarError.CODE_NOT_FUND));

        //校验旧密码是否正确
        if (StringUtils.isNotBlank(userInfo.getPassword())) {
            String oldPwd = StarUtils.getSHA256Str(materialDTO.getOldPassword());
            if (!oldPwd.equals(userInfo.getPassword())) {
                throw new StarException(StarError.OLD_PWD_FAIL);
            }
        }

        //校验距离上次修改密码是否超过24小时
        String changeSuccessKey = String.format(RedisKey.REDIS_CHANGE_PWD_SUCCESS_EXPIRED.getKey(), userInfo.getAccount());
        Object userIdObj = this.redisTemplate.opsForValue().get(changeSuccessKey);
        if (Objects.nonNull(userIdObj)) {
            new StarException(StarError.CHANGE_PWD_FREQUENCY_IS_TOO_HIGH);
        }

        //校验新密码与最新十次的修改是否一致
        String newPassword = StarUtils.getSHA256Str(materialDTO.getPassword());
        UserPwdChangeLogsVO userPwdChangeLogsVO = this.userRepository.queryPwdLog(userInfo.getAccount());
        Map<String, String> userPwdMap = Optional
                .ofNullable(userPwdChangeLogsVO.getPasswords())
                .orElse(new ArrayList<>())
                .stream()
                .collect(Collectors.toMap(Function.identity(), Function.identity()));
        if (Objects.nonNull(userPwdMap.get(newPassword))) {
            throw new StarException(StarError.PWD_NOT_CHANGE);
        }

        //更新密码
        Integer result = this.userRepository.changePwd(userInfo.getAccount(), materialDTO.getPassword());
        Boolean successBln = Objects.nonNull(result) ? Boolean.TRUE : Boolean.FALSE;

        //更新成功修改密码间隔时间
        this.redisTemplate.opsForValue().set(changeSuccessKey, userInfo.getAccount()
                , RedisKey.REDIS_CHANGE_PWD_SUCCESS_EXPIRED.getTime()
                , RedisKey.REDIS_CHANGE_PWD_SUCCESS_EXPIRED.getTimeUnit());

        return successBln;
    }

    @Override
    public Boolean intiPayPassword(PayPasswordDTO req) {
        //校验用户是否存在
        UserInfo userInfo = this.userRepository.queryUserInfoByUserId(req.getUserId());
        if (Objects.isNull(userInfo)) {
            throw new StarException(StarError.USER_NOT_EXISTS);
        }

        //校验验证码是否过期
        String redisKey = String.format(RedisKey.REDIS_CODE_LOGIN_CHANGE_PAYPWD.getKey(), req.getPhone());
        String code = String.valueOf(this.redisTemplate.opsForValue().get(redisKey));
        Optional.ofNullable(req)
                .filter(a -> a.getVerificationCode().equals(code))
                .orElseThrow(() -> new StarException(StarError.CODE_NOT_FUND));

        if (StringUtils.isNotBlank(userInfo.getPlyPassword())) {
            //初始化密码
            return this.userRepository.changePayPwd(userInfo.getAccount(), req.getPayPassword());
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean changePayPassword(PayPasswordDTO req) {
        //校验用户是否存在
        UserInfo userInfo = this.userRepository.queryUserInfoByUserId(req.getUserId());
        if (Objects.isNull(userInfo)) {
            throw new StarException(StarError.USER_NOT_EXISTS);
        }

        //校验验证码是否过期
        String redisKey = String.format(RedisKey.REDIS_CODE_LOGIN_CHANGE_PAYPWD.getKey(), req.getPhone());
        String code = String.valueOf(this.redisTemplate.opsForValue().get(redisKey));
        Optional.ofNullable(req)
                .filter(a -> a.getVerificationCode().equals(code))
                .orElseThrow(() -> new StarException(StarError.CODE_NOT_FUND));

        //校验距离上次修改密码是否超过24小时
        String changeSuccessKey = String.format(RedisKey.REDIS_CHANGE_PAY_PWD_SUCCESS_EXPIRED.getKey(), userInfo.getAccount());
        Object userIdObj = this.redisTemplate.opsForValue().get(changeSuccessKey);
        if (Objects.nonNull(userIdObj)) {
            new StarException(StarError.CHANGE_PWD_FREQUENCY_IS_TOO_HIGH);
        }

        //修改密码
        Boolean successBln = this.userRepository.changePayPwd(userInfo.getAccount(), req.getPayPassword());
        if (successBln) {
            this.redisTemplate.opsForValue().set(changeSuccessKey, userInfo.getAccount()
                    , RedisKey.REDIS_CHANGE_PAY_PWD_SUCCESS_EXPIRED.getTime()
                    , RedisKey.REDIS_CHANGE_PAY_PWD_SUCCESS_EXPIRED.getTimeUnit());
        }
        return successBln;
    }

    @Override
    public Boolean checkPayPassword(CheckPayPassword req) {
        // 前置校验凭证
        String preCheckKey = String.format(RedisKey.REDIS_PRE_PAY_PWD_CHECK_TOKEN.getKey(), req.getUserId());
        Optional.ofNullable(this.redisUtil.get(preCheckKey))
                .filter(realToken -> Objects.equals(realToken, req.getToken()))
                .orElseThrow(() -> new StarException(StarError.PAYPWD_PRE_CHECK_ERROR));

        // 判断连续错误次数是否超过限制
        String errorCountKey = String.format(RedisKey.REDIS_PAYPWD_CHECK_ERROR_TIMES.getKey(), req.getUserId());
        if (this.redisUtil.hasKey(errorCountKey) &&
                StarConstants.PAYPWD_RETRY_COUNT <= (Integer) this.redisUtil.get(errorCountKey)) {
            throw new StarException(StarError.PAYPWD_CHECK_FREEZE, String.format(StarError.PAYPWD_CHECK_FREEZE.getErrorMessage(), StarConstants.PAYPWD_RETRY_COUNT));
        }

        // 校验用户是否存在
        UserInfo userInfo = this.userRepository.queryUserInfoByUserId(req.getUserId());
        if (Objects.isNull(userInfo)) {
            throw new StarException(StarError.USER_NOT_EXISTS);
        }

        // 校验成功标识
        String checkSuccessKey = String.format(RedisKey.REDIS_PAY_PWD_CHECK_SUCCESS.getKey(), req.getUserId());

        // 校验密码是否正确
        String payPwd = StarUtils.getSHA256Str(req.getPayPassword());
        if (!Objects.equals(payPwd, userInfo.getPlyPassword())) {
            // 错误后删除成功标识
            this.redisUtil.del(checkSuccessKey);
            long errorCount = this.redisUtil.incr(errorCountKey, 1);
            this.redisUtil.expire(errorCountKey, RedisKey.REDIS_PAYPWD_CHECK_ERROR_TIMES.getTime(), RedisKey.REDIS_PAYPWD_CHECK_ERROR_TIMES.getTimeUnit());
            throw new StarException(StarError.PAYPWD_CHECK_ERROR, String.format(StarError.PAYPWD_CHECK_ERROR.getErrorMessage(), StarConstants.PAYPWD_RETRY_COUNT, errorCount));
        }

        // 清空错误次数和前置校验凭证
        this.redisUtil.del(errorCountKey, preCheckKey);

        // 保存校验成功标识 供后续业务校验使用 有效期5分钟 使用完需删除
        this.redisUtil.set(checkSuccessKey, Boolean.TRUE.toString(), RedisKey.REDIS_PAY_PWD_CHECK_SUCCESS.getTime(), RedisKey.REDIS_PAY_PWD_CHECK_SUCCESS.getTimeUnit());
        return Boolean.TRUE;
    }

    @Override
    public Boolean realNameAuthentication(Long userId, AuthenticationNameDTO req) {

        //校验用户信息
        UserInfo userInfo = this.userRepository.queryUserInfoByUserId(userId);
        if (Objects.isNull(userInfo)) {
            throw new StarException(StarError.USER_NOT_EXISTS);
        }

        if (this.smsEnable) {
            //校验验证码
            String redisVerificationCodeKey = String.format(RedisKey.REDIS_REAL_NAME_AUTHENTICATION.getKey(), userId);
            String code = String.valueOf(this.redisTemplate.opsForValue().get(redisVerificationCodeKey));
            if (StringUtils.isBlank(code)) {
                throw new StarException(StarError.CODE_NOT_FUND);
            }
        }

        //校验用户是否实名认证
        if (YesOrNoStatusEnum.YES.getCode().equals(userInfo.getRealPersonFlag())) {
            throw new StarException(StarError.IS_REAL_NAME_AUTHENTICATION);
        }

        //todo 校验当日是否超过限制次数
        //todo 调用xxx云完成实名认证,可能不需要，节约用钱


        //保存认证信息
        UserInfoUpdateDTO updateDTO = new UserInfoUpdateDTO();
        updateDTO.setAccount(userInfo.getAccount());
        updateDTO.setFullName(req.getFullName());
        updateDTO.setIdNumber(req.getIdNumber());
        updateDTO.setRealPersonFlag(YesOrNoStatusEnum.YES.getCode());
        Integer row = this.userRepository.updateUserInfo(updateDTO);
        return row > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public UserAuthenticationVO queryAuthentication(Long userId) {
        UserInfo userInfo = this.userRepository.queryUserInfoByUserId(userId);
        Optional.ofNullable(userInfo.getRealPersonFlag())
                .filter(a -> Objects.equals(YesOrNoStatusEnum.YES.getCode(), a))
                .orElseThrow(() -> new StarException(StarError.NOT_AUTHENTICATION));

        return UserAuthenticationVO.builder().authenticationData("您已实名认证").build();
    }

    @Override
    public AgreementVO queryAgreementContentById(String agreementId) {
        return null;
    }

    @Override
    public AgreementVO queryAgreementContentByType(Integer agreementType) {

        return null;
    }

    @Override
    public Boolean saveUserAgreementHistoryByUserId(AgreementIdDTO agreementIdDTO) {
        return null;
    }

    @Override
    public List<AgreementVO> queryAgreementByAgreementId(List<String> agreementIdList) {
        List<AgreementVO> agreementInfos = this.userRepository.queryAgreementByAgreementId(agreementIdList);
        return agreementInfos;
    }

    @Transactional
    @Override
    public void batchInsertAgreementSign(List<AgreementSignDTO> list, Long userId, Long authorizationId) {
        this.userRepository.addAuthorizationId(userId, authorizationId);
        this.userRepository.batchInsertAgreementSign(list);
    }

    @Override
    public UserRealInfo getUserRealInfo(Long uid) {
        return this.userRepository.getUserInfoAll(uid);
    }

    @Override
    public Boolean modifyUserInfo(UserInfoUpdateDTO req) {
        return this.userRepository.updateUserInfo(req) == 1;
    }

    @Override
    public String prePayPasswordCheck(Long uid) {
        String token = StarUtils.getVerifyCode();
        // 保存前置校验凭证
        String redisKey = String.format(RedisKey.REDIS_PRE_PAY_PWD_CHECK_TOKEN.getKey(), uid);
        this.redisUtil.set(redisKey, token, RedisKey.REDIS_PRE_PAY_PWD_CHECK_TOKEN.getTime(), RedisKey.REDIS_PRE_PAY_PWD_CHECK_TOKEN.getTimeUnit());
        return token;
    }

    @Override
    public void assertPayPwdCheckSuccess(Long uid) {
        // 校验成功标识
        String checkSuccessKey = String.format(RedisKey.REDIS_PAY_PWD_CHECK_SUCCESS.getKey(), uid);
        Assert.isTrue(this.redisUtil.hasKey(checkSuccessKey), () -> new StarException(StarError.PAYPWD_PRE_CHECK_ERROR));
        // 使用后删除成功标识
        this.redisUtil.del(checkSuccessKey);
    }

    @Override
    public Boolean resetPassword(AuthMaterialDTO materialDTO) {
        //校验用户是否存在
        UserInfo userInfo = this.userRepository.queryUserInfoByPhone(materialDTO.getPhone());
        if (Objects.isNull(userInfo)) {
            log.error("设置初始密码，用户：{}不存在", materialDTO.getPhone());
            throw new StarException(StarError.USER_NOT_EXISTS);
        }

        //验证码校验
        String verifiCodeKey = String.format(RedisKey.REDIS_CODE_LOGIN_CHANGE_PWD.getKey(), materialDTO.getPhone());
        String code = String.valueOf(this.redisTemplate.opsForValue().get(verifiCodeKey));
        if (!code.equals(materialDTO.getVerificationCode())) {
            throw new StarException(StarError.CODE_NOT_FUND);
        }

        Integer updateRows = this.userRepository.setUpPassword(userInfo, materialDTO.getPassword());
        return Objects.nonNull(updateRows) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Boolean queryIsSettingPwd(Long id) {
        Boolean isSetting = redisTemplate.opsForValue().getBit(RedisKey.REDIS_USER_IS_SETTING_PWD.getKey(), id);
        if (!isSetting) {
            UserInfo userInfo = userRepository.queryUserInfoByUserId(id);
            isSetting = Objects.isNull(userInfo.getPlyPassword());
            redisTemplate.opsForValue().setBit(RedisKey.REDIS_USER_IS_SETTING_PWD.getKey(), id, isSetting);
        }
        return isSetting;
    }

    @Override
    public Boolean plyPasswordSetting(UserInfoUpdateDTO userInfoUpdateDTO) {

        Optional.ofNullable(userInfoUpdateDTO.getPlyPassword()).orElseThrow(() -> new StarException("密码不能为空"));

        //密码长度校验
        if (userInfoUpdateDTO.getPlyPassword().length() != 6) {
            throw new StarException("密码长度为6位");
        }

        //加密
        userInfoUpdateDTO.setPlyPassword(StarUtils.getSHA256Str(userInfoUpdateDTO.getPlyPassword()));

        UserInfoVO userInfoVO = queryUserInfo(userInfoUpdateDTO.getAccount());
        Optional.ofNullable(userInfoVO).orElseThrow(() -> new StarException("用户不存在"));

        if (StringUtils.isNotBlank(userInfoVO.getPlyPassword())) {
            if (StringUtils.isNotBlank(userInfoUpdateDTO.getOldPlyPassword()) &&
                    !(StarUtils.getSHA256Str(userInfoUpdateDTO.getOldPlyPassword()).equals(userInfoVO.getPlyPassword()))) {
                throw new StarException("支付密码不正确");
            }
        }

        if (userInfoVO.getPlyPassword() != null && userInfoVO.getPlyPassword().equals(userInfoUpdateDTO.getPlyPassword())) {
            throw new StarException("修改的密码不能和当前一样");
        }

        return modifyUserInfo(userInfoUpdateDTO);
    }
}
