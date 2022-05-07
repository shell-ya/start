package com.starnft.star.infrastructure.repository.user.strategy;

import cn.hutool.crypto.digest.MD5;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.model.dto.UserLoginDTO;
import com.starnft.star.infrastructure.entity.user.UserInfoEntity;
import com.starnft.star.infrastructure.mapper.UserInfoMapper;
import com.starnft.star.infrastructure.repository.user.UserAdapterService;
import com.starnft.star.infrastructure.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author WeiChunLAI
 */
@Component("loginByPasswordStrategy")
public class LoginByPasswordStrategy extends UserLoginStrategy{

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserAdapterService userAdapterService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long saveLoginInfo(UserLoginDTO userLoginDTO) {
        //todo 必填参数校验

        //校验用户是否存在
        UserInfoEntity userQuery = new UserInfoEntity();
        userQuery.setIsDeleted(Boolean.FALSE);
        userQuery.setPhone(userLoginDTO.getPhone());
        UserInfoEntity userInfoEntity = userInfoMapper.selectOne(userQuery);
        if (Objects.isNull(userInfoEntity)) {
            throw new StarException(StarError.USER_NOT_EXISTS, "用户不存在，请先注册");
        }

        Integer errorTimes = userAdapterService.checkUserFreezeByPassword(userInfoEntity.getAccount());
        Integer verificationCodeErrorTimes = userAdapterService.checkUserFreezeByVerificationCode(userInfoEntity.getAccount());

        //校验密码是否正确
        if (StringUtils.isNotBlank(userInfoEntity.getPassword())){
            String passwordHash = MD5.create().digestHex(userLoginDTO.getPassword());

            if (!passwordHash.equals(userInfoEntity.getPassword())){
                if (null == errorTimes){
                    errorTimes = 1;
                }else {
                    errorTimes ++;
                }

                //设置错误次数
                String retryPwdKey = String.format(RedisKey.RETRY_PWD.getKey(),userInfoEntity.getAccount());
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
            throw new StarException(StarError.USER_NOT_EXISTS, "您输入的密码有误，请重试");
        }

        //登录成功后，清除失败的次数
        userAdapterService.clearLoginFailureRecord(userInfoEntity.getAccount() , errorTimes ,verificationCodeErrorTimes);

        return userInfoEntity.getAccount();
    }


}
