package com.starnft.star.domain.user.service;

import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.component.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author WeiChun
 */
@Component
public class UserAdapterService {

    @Autowired
    RedisUtil redisUtil;

    public Integer checkUserFreezeByPassword(Long userId){
        //校验用户是否输入密码失败5次以上
        String retryPwdKey = String.format(RedisKey.RETRY_PWD.getKey(), userId);
        Integer errorTimes = (Integer) redisUtil.get(retryPwdKey);
        if (null != errorTimes && StarConstants.RETRY_COUNT <= errorTimes){
            throw new StarException(StarError.USER_NOT_EXISTS, "您输入的密码连续输错 "+ StarConstants.RETRY_COUNT+" 次，为了您的账户安全，账户已冻结，请明日重试");
        }
        return errorTimes;
    }

    public void clearLoginFailureRecord(Long userId, Integer errorTimes , Integer verifyCodeErrorTimes){
        String retryPwdKey = String.format(RedisKey.RETRY_PWD.getKey(), userId);
        if (null != errorTimes){
            redisUtil.delByKey(retryPwdKey);
        }
        if (null != verifyCodeErrorTimes){
            redisUtil.delByKey(String.format(RedisKey.SMS_CODE.getKey(), userId));
        }
    }

    public Integer checkUserFreezeByVerificationCode(Long userId){
        //校验用户是否输入验证码失败10次以上
        String key = String.format(RedisKey.RETRY_PWD.getKey(), userId);
        Integer verificationErrorTimes = (Integer) redisUtil.get(key);
        if (Objects.nonNull(verificationErrorTimes) && StarConstants.VERIFY_CODE_ERROR_TIMES <= verificationErrorTimes){
            throw new StarException(StarError.USER_HAS_BEEN_FROZEN_BY_VERIFICATION_CODE_ERROR);
        }
        return verificationErrorTimes;
    }
}
