package com.starnft.star.domain.user.service.strategy;

import cn.hutool.crypto.digest.MD5;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.StarUtils;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.domain.user.service.UserAdapterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * @author WeiChunLAI
 */
@Component("loginByPasswordStrategy")
public class LoginByPasswordStrategy extends UserLoginStrategy{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserAdapterService userAdapterService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long saveLoginInfo(UserLoginDTO userLoginDTO) {

        //校验用户是否存在
        UserInfo userInfo = userRepository.queryUserInfoByPhone(userLoginDTO.getPhone());
        if (Objects.isNull(userInfo)) {
            throw new StarException(StarError.USER_NOT_EXISTS, "用户不存在，请先注册");
        }

        Integer errorTimes = userAdapterService.checkUserFreezeByPassword(userInfo.getAccount());
        Integer verificationCodeErrorTimes = userAdapterService.checkUserFreezeByVerificationCode(userInfo.getAccount());

        //校验密码是否正确
        if (StringUtils.isNotBlank(userInfo.getPassword())){
            String passwordHash = StarUtils.getSHA256Str(userLoginDTO.getPassword());

            if (!passwordHash.equals(userInfo.getPassword())){
                if (null == errorTimes){
                    errorTimes = 1;
                }else {
                    errorTimes ++;
                }

                //设置错误次数
                String retryPwdKey = String.format(RedisKey.RETRY_PWD.getKey(), userInfo.getAccount());
                redisTemplate.opsForValue().set(retryPwdKey , errorTimes , RedisKey.RETRY_PWD.getTime() , RedisKey.RETRY_PWD.getTimeUnit());

                if (StarConstants.RETRY_COUNT.equals(errorTimes)){
                    throw new StarException(StarError.USER_NOT_EXISTS, "账号已被冻结，请明日再重试");
                }

                if (4 <= errorTimes){
                    throw new StarException(StarError.USER_NOT_EXISTS, "您输入的密码有误，已连续输错"+ errorTimes +"次，若连续输错 "+ StarConstants.RETRY_COUNT+" 次，账号将被冻结");
                }
                throw new StarException(StarError.USER_NOT_EXISTS, "您输入的密码有误，请重试");
            }
        }else {
            throw new StarException(StarError.PWD_NOT_SETTING);
        }

        //登录成功后，清除失败的次数
        userAdapterService.clearLoginFailureRecord(userInfo.getAccount() , errorTimes ,verificationCodeErrorTimes);

        return userInfo.getAccount();
    }


}
