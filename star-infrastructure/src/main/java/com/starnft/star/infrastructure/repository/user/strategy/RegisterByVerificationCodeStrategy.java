package com.starnft.star.infrastructure.repository.user.strategy;

import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.domain.model.dto.UserLoginDTO;
import com.starnft.star.infrastructure.entity.user.UserInfoEntity;
import com.starnft.star.infrastructure.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Component("registerByVerificationCodeStrategy")
public class RegisterByVerificationCodeStrategy extends UserRegisterStrategy{

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long startSaveRegisterInfo(UserLoginDTO registerInfo) {
        //todo 必填参数校验

        //校验验证码
        String key = String.format(RedisKey.REDIS_CODE_REGISIER.getKey(), registerInfo.getPhone());
        String smsCode =  String.valueOf(redisTemplate.opsForValue().get(key));
        if (smsCode.equals(registerInfo.getCode())){
            throw new StarException(StarError.CODE_NOT_FUND);
        }



        //如果该账号已经注册，则报错
        UserInfoEntity queryInfo  = new UserInfoEntity();
        queryInfo.setPhone(registerInfo.getPhone());
        queryInfo.setIsDeleted(Boolean.FALSE);
        UserInfoEntity userInfo = userInfoMapper.selectOne(queryInfo);
        if (Objects.nonNull(userInfo)){
            throw new StarException(StarError.USER_REPEATED);
        }

        Long userId = SnowflakeWorker.generateId();

        //用户信息入库
        queryInfo.setNickName("一位聪明的NFT玩家");
        queryInfo.setAccount(userId);
        queryInfo.setCreatedBy(userId);
        queryInfo.setCreatedAt(new Date());
        queryInfo.setModifiedBy(userId);
        queryInfo.setModifiedAt(new Date());
        userInfoMapper.insert(queryInfo);

        return userId;
    }

}
