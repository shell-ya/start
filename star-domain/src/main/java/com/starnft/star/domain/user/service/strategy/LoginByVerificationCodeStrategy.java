package com.starnft.star.domain.user.service.strategy;

import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.enums.LoginTypeEnum;
import com.starnft.star.common.enums.RegisterTypeEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.domain.user.service.UserAdapterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Component("loginByVerificationCodeStrategy")
public class LoginByVerificationCodeStrategy extends UserLoginStrategy{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserAdapterService userAdapterService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Long saveLoginInfo(UserLoginDTO userLoginDTO) {
//        Optional.ofNullable(userLoginDTO.getPhone())
//                .orElseThrow(() -> new  StarException(StarError.PARAETER_UNSUPPORTED , "phone 不能为空"));
//        Optional.ofNullable(userLoginDTO.getCode())
//                .orElseThrow(() -> new  StarException(StarError.PARAETER_UNSUPPORTED , "code 不能为空"));

        UserInfo userInfo = userRepository.queryUserInfoByPhone(userLoginDTO.getPhone());
        Long userId = null;
        if (Objects.isNull(userInfo)) {

//            //校验code
//            String key = String.format(RedisKey.REDIS_CODE_REGISIER.getKey(), userLoginDTO.getPhone());
//            String smsCode =  String.valueOf(redisTemplate.opsForValue().get(key));
//
//            if (!smsCode.equals(userLoginDTO.getCode())){
//                throw new StarException(StarError.CODE_NOT_FUND);
//            }

            //注册账号
            UserRegisterStrategy userRegisterStrategy = applicationContext.getBean(RegisterTypeEnum.SMS_CODE_REGISTER.getStrategy(), UserRegisterStrategy.class);
            userId = userRegisterStrategy.register(userLoginDTO);

        } else {
           userId = userInfo.getAccount();

           //校验用户是否被冻结
            Integer errorTimes = userAdapterService.checkUserFreezeByPassword(userId);
            Integer verificationErrorTimes = userAdapterService.checkUserFreezeByVerificationCode(userId);


            //校验code
            String key = String.format(RedisKey.REDIS_CODE_REGISIER.getKey(), userLoginDTO.getPhone());
            String smsCode =  String.valueOf(redisTemplate.opsForValue().get(key));

            if (!StringUtils.equals(userLoginDTO.getCode(),smsCode)){

                // 验证码错误，记录错误次数
                if (Objects.isNull(verificationErrorTimes)) {
                    verificationErrorTimes =1 ;
                }else {
                    verificationErrorTimes++;
                }

                //设置错误次数
                String verificationErrorTimesKey = String.format(RedisKey.SMS_CODE.getKey() , userInfo.getAccount());
                redisTemplate.opsForValue().set(verificationErrorTimesKey , verificationErrorTimes , RedisKey.SMS_CODE.getTime() , RedisKey.SMS_CODE.getTimeUnit());

                if (StarConstants.VERIFY_CODE_ERROR_TIMES.equals(verificationErrorTimes)){
                    throw new StarException(StarError.USER_HAS_BEEN_FROZEN_BY_VERIFICATION_CODE_ERROR , String.format(StarError.USER_HAS_BEEN_FROZEN_BY_VERIFICATION_CODE_ERROR.getErrorMessage(), StarConstants.VERIFY_CODE_ERROR_TIMES));
                }

                if (4 <= verificationErrorTimes){
                    throw new StarException(StarError.CODE_NOT_FUND);
                }

                throw new StarException(StarError.CODE_NOT_FUND);
            }

            userAdapterService.clearLoginFailureRecord(userId, errorTimes , verificationErrorTimes);
        }

        return userId;
    }
}
