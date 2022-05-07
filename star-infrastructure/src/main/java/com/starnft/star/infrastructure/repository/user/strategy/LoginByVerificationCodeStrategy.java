package com.starnft.star.infrastructure.repository.user.strategy;

import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.model.dto.UserLoginDTO;
import com.starnft.star.infrastructure.entity.user.UserInfoEntity;
import com.starnft.star.infrastructure.enums.LoginTypeEnum;
import com.starnft.star.infrastructure.mapper.UserInfoMapper;
import com.starnft.star.infrastructure.repository.user.UserAdapterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Component("loginByVerificationCodeStrategy")
public class LoginByVerificationCodeStrategy extends UserLoginStrategy{

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserAdapterService userAdapterService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Long saveLoginInfo(UserLoginDTO userLoginDTO) {
        //todo 校验必填参数

        UserInfoEntity queryUser = new UserInfoEntity();
        queryUser.setIsDeleted(Boolean.FALSE);
        queryUser.setPhone(userLoginDTO.getPhone());
        UserInfoEntity userInfo = userInfoMapper.selectOne(queryUser);

        Long userId = null;
        if (Objects.isNull(userInfo)) {

            //校验code
            String key = String.format(RedisKey.REDIS_CODE_REGISIER.getKey(), userId);
            String smsCode =  String.valueOf(redisTemplate.opsForValue().get(key));

            if (smsCode.equals(userLoginDTO.getCode())){
                throw new StarException(StarError.CODE_NOT_FUND);
            }

            //注册账号
            UserRegisterStrategy userRegisterStrategy = applicationContext.getBean(LoginTypeEnum.PHONE_CODE_LOGIN.getStrategy(), UserRegisterStrategy.class);
            userId = userRegisterStrategy.register(userLoginDTO);

        } else {
           userId = userInfo.getAccount();

           //校验用户是否被冻结
            Integer errorTimes = userAdapterService.checkUserFreezeByPassword(userId);
            Integer verificationErrorTimes = userAdapterService.checkUserFreezeByVerificationCode(userId);


            //校验code
            String key = String.format(RedisKey.REDIS_CODE_REGISIER.getKey(), userId);
            String smsCode =  String.valueOf(redisTemplate.opsForValue().get(key));

            if (!StringUtils.equals(userLoginDTO.getCode(),smsCode)){

                // 验证码错误，记录错误次数
                if (Objects.isNull(verificationErrorTimes)) {
                    verificationErrorTimes =1 ;
                }else {
                    verificationErrorTimes++;
                }

                //设置错误次数
                redisTemplate.opsForValue().set(key , verificationErrorTimes , RedisKey.REDIS_CODE_REGISIER.getTime() , RedisKey.REDIS_CODE_REGISIER.getTimeUnit());

                if (StarConstants.VERIFY_CODE_ERROR_TIMES.equals(verificationErrorTimes)){
                    throw new StarException(StarError.USER_HAS_BEEN_FROZEN_BY_VERIFICATION_CODE_ERROR , String.format(StarError.USER_HAS_BEEN_FROZEN_BY_VERIFICATION_CODE_ERROR.getErrorMessage(), StarConstants.VERIFY_CODE_ERROR_TIMES));
                }

                if (4 <= verificationErrorTimes){
                    throw new StarException(StarError.CODE_NOT_FUND);
                }
            }

            userAdapterService.clearLoginFailureRecord(userId, errorTimes , verificationErrorTimes);
        }

        return userId;
    }
}
