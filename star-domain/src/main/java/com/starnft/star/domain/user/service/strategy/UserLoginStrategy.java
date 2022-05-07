package com.starnft.star.domain.user.service.strategy;

import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Component("userLoginStrategy")
public abstract class UserLoginStrategy {


    public abstract Long saveLoginInfo(UserLoginDTO userLoginDTO);


    public Long login(UserLoginDTO userLoginDTO){

        //用户信息入库
        Long userId = saveLoginInfo(userLoginDTO);

        if (Objects.isNull(userId)){
            return userId;
        }

        //todo 用户登录日志 入库
        return userId;
    }
}
