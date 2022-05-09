package com.starnft.star.domain.user.service.strategy;

import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.domain.user.model.dto.UserInfoAdd;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Component("registerByVerificationCodeStrategy")
public class RegisterByVerificationCodeStrategy extends UserRegisterStrategy{

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Long startSaveRegisterInfo(UserLoginDTO registerInfo) {
        //todo 必填参数校验

        //校验验证码
        String key = String.format(RedisKey.REDIS_CODE_REGISIER.getKey(), registerInfo.getPhone());
        String smsCode =  String.valueOf(redisTemplate.opsForValue().get(key));
        if (!smsCode.equals(registerInfo.getCode())){
            throw new StarException(StarError.CODE_NOT_FUND);
        }

        //如果该账号已经注册，则报错
        UserInfo userInfo = userRepository.queryUserInfoByPhone(registerInfo.getPhone());
        if (Objects.nonNull(userInfo)){
            throw new StarException(StarError.USER_REPEATED);
        }

        Long userId = SnowflakeWorker.generateId();

        //用户信息入库
        UserInfoAdd userInfoAdd = new UserInfoAdd();
        userInfoAdd.setCreateBy(userId);
        userInfoAdd.setNickName("耿直的NFT玩家");
        userInfoAdd.setUserId(userId);
        userInfoAdd.setPhone(registerInfo.getPhone());
        userRepository.addUserInfo(userInfoAdd);

        return userId;
    }

}
