package com.starnft.star.domain.user.service.strategy;

import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.InvitationCodeUtil;
import com.starnft.star.domain.user.model.dto.UserInfoAddDTO;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Component("registerByVerificationCodeStrategy")
public class RegisterByVerificationCodeStrategy extends UserRegisterStrategy {

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
        if (Objects.nonNull(userInfo)) {
            throw new StarException(StarError.USER_REPEATED);
        }


        //用户信息入库
        UserInfoAddDTO userInfoAddDTO = new UserInfoAddDTO();
        userInfoAddDTO.setNickName("玩家 " + RandomStringUtils.randomAlphanumeric(9));
        userInfoAddDTO.setPhone(registerInfo.getPhone());
        //上级
        activityParent(registerInfo, userInfoAddDTO);

        return userRepository.addUserInfo(userInfoAddDTO);

    }

    //上级参数封装
    private void activityParent(UserLoginDTO registerInfo, UserInfoAddDTO userInfoAddDTO) {
        if (StringUtils.isNotEmpty(registerInfo.getSc()) && StringUtils.isNotBlank(registerInfo.getSc())) {
            log.info("进入的邀请码为{}", registerInfo.getSc());
            Long parent = InvitationCodeUtil.decode(registerInfo.getSc());
            userInfoAddDTO.setParent(parent);

        }
    }

}
