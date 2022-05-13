package com.starnft.star.domain.user.service.impl;

import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.YesOrNoStatusEnum;
import com.starnft.star.common.enums.LoginTypeEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.po.AccessToken;
import com.starnft.star.common.utils.StarUtils;
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

import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
    private IUserRepository userRepository;

    @Value("${star.sms.enable: false}")
    private Boolean smsEnable;

    @Override
    public UserInfoVO login(UserLoginDTO req) {

        LoginTypeEnum loginTypeEnum = LoginTypeEnum.getLoginTypeEnum(req.getLoginScenes());
        UserLoginStrategy login = applicationContext.getBean(loginTypeEnum.getStrategy(), UserLoginStrategy.class);
        Long userId = login.login(req);

        //创建token
        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(userId);

        UserInfoVO userInfo = new UserInfoVO();
        userInfo.setToken(createUserTokenAndSaveRedis(userId));
        userInfo.setUserId(userId);

        return userInfo;
    }

    @Override
    public UserRegisterInfoVO loginByPhone(UserLoginDTO req) {

        LoginTypeEnum loginTypeEnum = LoginTypeEnum.getLoginTypeEnum(req.getLoginScenes());
        UserLoginStrategy login = applicationContext.getBean(loginTypeEnum.getStrategy(), UserLoginStrategy.class);
        Long userId = login.login(req);

        UserRegisterInfoVO userRegisterInfoVO = new UserRegisterInfoVO();

        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(userId);
        userRegisterInfoVO.setToken(createUserTokenAndSaveRedis(userId));
        userRegisterInfoVO.setUserId(userId);

        return userRegisterInfoVO;
    }

    @Transactional
    @Override
    public Boolean logOut(Long userId) {
        userRepository.deleteLoginLog(userId);
        return cleanUserToken(userId);
    }

    @Override
    public UserVerifyCode getVerifyCode(UserVerifyCodeDTO req) {
        //发送验证码
        String code = StarUtils.getVerifyCode();

        //校验验证码发送的频率
        String key = String.format(RedisKey.SMS_CODE_LIFE.getKey(), req.getPhone());
        if (smsEnable) {
            Object obj = redisTemplate.opsForValue().get(key);
            if (Objects.isNull(obj)) {
                throw new StarException(StarError.VERIFYCODE_FREQUENCY_IS_TOO_HIGH);
            }
            //todo 调用服务商发送短信

        }

        //录入redis
        RedisKey redisKeyEnum = RedisKey.getRedisKeyEnum(req.getVerificationScenes());
        String redisKey = String.format(redisKeyEnum.getKey(), req.getPhone());
        try {
            redisTemplate.opsForValue().set(redisKey, code, redisKeyEnum.getTime(), redisKeyEnum.getTimeUnit());
            //限制短信发送时间
            redisTemplate.opsForValue().set(key, req.getPhone(), RedisKey.SMS_CODE_LIFE.getTime(), RedisKey.SMS_CODE_LIFE.getTimeUnit());
        } catch (Exception e) {
            log.error("sms redis error:{}", e);
        }

        // 如发送短信则不返回验证码
        return smsEnable ? null : UserVerifyCode.builder().code(code).build();
    }

    @Override
    public Boolean setUpPassword(AuthMaterialDTO materialDTO) {
        //校验用户是否存在
        UserInfo userInfo = userRepository.queryUserInfoByPhone(materialDTO.getPhone());
        if (Objects.isNull(userInfo)) {
            log.error("设置初始密码，用户：{}不存在", materialDTO.getPhone());
            throw new StarException(StarError.USER_NOT_EXISTS);
        }

        //验证码校验
        String verifiCodeKey = String.format(RedisKey.REDIS_CODE_LOGIN_CHANGE_PWD.getKey(), materialDTO.getPhone());
        String code = String.valueOf(redisTemplate.opsForValue().get(verifiCodeKey));
        if (!code.equals(materialDTO.getVerificationCode())) {
            throw new StarException(StarError.CODE_NOT_FUND);
        }

        Integer updateRows = userRepository.setUpPassword(userInfo, materialDTO.getPassword());
        return Objects.nonNull(updateRows) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Boolean changePassword(AuthMaterialDTO materialDTO) {
        //校验用户是否存在
        UserInfo userInfo = userRepository.queryUserInfoByPhone(materialDTO.getPhone());
        if (Objects.isNull(userInfo)) {
            log.error("修改密码，用户：{} 不存在", materialDTO.getPhone());
            throw new StarException(StarError.USER_NOT_EXISTS);
        }

        //校验验证码是否过期
        String redisKey = String.format(RedisKey.REDIS_CODE_NOT_LOGIN_CHANGE_PWD.getKey(), materialDTO.getPhone());
        String code = String.valueOf(redisTemplate.opsForValue().get(redisKey));
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
        Object userIdObj = redisTemplate.opsForValue().get(changeSuccessKey);
        if (Objects.nonNull(userIdObj)) {
            new StarException(StarError.CHANGE_PWD_FREQUENCY_IS_TOO_HIGH);
        }

        //校验新密码与最新十次的修改是否一致
        String newPassword = StarUtils.getSHA256Str(materialDTO.getPassword());
        UserPwdChangeLogsVO userPwdChangeLogsVO = userRepository.queryPwdLog(userInfo.getAccount());
        Map<String, String> userPwdMap = Optional
                .ofNullable(userPwdChangeLogsVO.getPasswords())
                .orElse(new ArrayList<>())
                .stream()
                .collect(Collectors.toMap(Function.identity(), Function.identity()));
        if (Objects.nonNull(userPwdMap.get(newPassword))) {
            throw new StarException(StarError.PWD_NOT_CHANGE);
        }

        //更新密码
        Integer result = userRepository.changePwd(userInfo.getAccount(), materialDTO.getPassword());
        Boolean successBln = Objects.nonNull(result) ? Boolean.TRUE : Boolean.FALSE;

        //更新成功修改密码间隔时间
        redisTemplate.opsForValue().set(changeSuccessKey, userInfo.getAccount()
                , RedisKey.REDIS_CHANGE_PWD_SUCCESS_EXPIRED.getTime()
                , RedisKey.REDIS_CHANGE_PWD_SUCCESS_EXPIRED.getTimeUnit());

        return successBln;
    }

    @Override
    public Boolean intiPayPassword(PayPasswordDTO req) {
        //校验用户是否存在
        UserInfo userInfo = userRepository.queryUserInfoByUserId(req.getUserId());
        if (Objects.isNull(userInfo)) {
            throw new StarException(StarError.USER_NOT_EXISTS);
        }

        //校验验证码是否过期
        String redisKey = String.format(RedisKey.REDIS_CODE_LOGIN_CHANGE_PAYPWD.getKey(), req.getPhone());
        String code = String.valueOf(redisTemplate.opsForValue().get(redisKey));
        Optional.ofNullable(req)
                .filter(a -> a.getVerificationCode().equals(code))
                .orElseThrow(() -> new StarException(StarError.CODE_NOT_FUND));

        if (StringUtils.isNotBlank(userInfo.getPlyPassword())) {
            //初始化密码
            return userRepository.changePayPwd(userInfo.getAccount(), req.getPayPassword());
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean changePayPassword(PayPasswordDTO req) {
        //校验用户是否存在
        UserInfo userInfo = userRepository.queryUserInfoByUserId(req.getUserId());
        if (Objects.isNull(userInfo)) {
            throw new StarException(StarError.USER_NOT_EXISTS);
        }

        //校验验证码是否过期
        String redisKey = String.format(RedisKey.REDIS_CODE_LOGIN_CHANGE_PAYPWD.getKey(), req.getPhone());
        String code = String.valueOf(redisTemplate.opsForValue().get(redisKey));
        Optional.ofNullable(req)
                .filter(a -> a.getVerificationCode().equals(code))
                .orElseThrow(() -> new StarException(StarError.CODE_NOT_FUND));

        //校验距离上次修改密码是否超过24小时
        String changeSuccessKey = String.format(RedisKey.REDIS_CHANGE_PAY_PWD_SUCCESS_EXPIRED.getKey(), userInfo.getAccount());
        Object userIdObj = redisTemplate.opsForValue().get(changeSuccessKey);
        if (Objects.nonNull(userIdObj)) {
            new StarException(StarError.CHANGE_PWD_FREQUENCY_IS_TOO_HIGH);
        }

        //修改密码
        Boolean successBln = userRepository.changePayPwd(userInfo.getAccount(), req.getPayPassword());
        if (successBln) {
            redisTemplate.opsForValue().set(changeSuccessKey, userInfo.getAccount()
                    , RedisKey.REDIS_CHANGE_PAY_PWD_SUCCESS_EXPIRED.getTime()
                    , RedisKey.REDIS_CHANGE_PAY_PWD_SUCCESS_EXPIRED.getTimeUnit());
        }
        return successBln;
    }

    @Override
    public Boolean checkPayPassword(CheckPayPassword req) {
        //校验用户是否存在
        UserInfo userInfo = userRepository.queryUserInfoByUserId(req.getUserId());
        if (Objects.isNull(userInfo)) {
            throw new StarException(StarError.USER_NOT_EXISTS);
        }

        //校验密码是否正确
        String payPwd = StarUtils.getSHA256Str(req.getPayPassword());
        if (!payPwd.equals(userInfo.getPlyPassword())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean realNameAuthentication(AuthenticationNameDTO req) {

        //校验用户信息
        UserInfo userInfo = userRepository.queryUserInfoByUserId(req.getUserId());
        if (Objects.isNull(userInfo)) {
            throw new StarException(StarError.USER_NOT_EXISTS);
        }

        if (smsEnable) {
            //校验验证码
            String redisVerificationCodeKey = String.format(RedisKey.REDIS_REAL_NAME_AUTHENTICATION.getKey(), req.getUserId());
            String code = String.valueOf(redisTemplate.opsForValue().get(redisVerificationCodeKey));
            if (StringUtils.isBlank(code)) {
                throw new StarException(StarError.CODE_NOT_FUND);
            }
        }

        //校验用户是否实名认证
        if (YesOrNoStatusEnum.YES.getCode().equals(userInfo.getRealPersonFlag())){
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
        Integer row = userRepository.updateUserInfo(updateDTO);
        return row > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public UserAuthenticationVO queryAuthentication(Long userId) {
        UserInfo userInfo = userRepository.queryUserInfoByUserId(userId);
        Optional.ofNullable(userInfo.getRealPersonFlag())
                .filter(a -> Objects.equals(YesOrNoStatusEnum.YES , a))
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
}
