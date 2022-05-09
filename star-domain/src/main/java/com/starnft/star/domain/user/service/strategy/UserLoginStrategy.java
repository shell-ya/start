package com.starnft.star.domain.user.service.strategy;

import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Component("userLoginStrategy")
public abstract class UserLoginStrategy {

    @Autowired
    private IUserRepository userRepository;

    public abstract Long saveLoginInfo(UserLoginDTO userLoginDTO);


    public Long login(UserLoginDTO userLoginDTO){

        //用户信息入库
        Long userId = saveLoginInfo(userLoginDTO);

        if (Objects.isNull(userId)){
            return userId;
        }

        //保存登录日志
        userRepository.addLoginLog(userId);

        return userId;
    }
}
