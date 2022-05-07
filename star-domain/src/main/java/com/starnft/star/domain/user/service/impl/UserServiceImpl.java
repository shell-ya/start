package com.starnft.star.domain.user.service.impl;

import com.starnft.star.common.component.AuthTokenComponent;
import com.starnft.star.common.enums.LoginTypeEnum;
import com.starnft.star.common.po.AccessToken;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.model.vo.UserInfoVO;
import com.starnft.star.domain.user.model.vo.UserRegisterInfoVO;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.domain.user.service.IUserService;
import com.starnft.star.domain.user.service.strategy.UserLoginStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private AuthTokenComponent authTokenComponent;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public UserInfoVO login(UserLoginDTO req) {

        LoginTypeEnum loginTypeEnum = LoginTypeEnum.getLoginTypeEnum(req.getLoginScenes());
        UserLoginStrategy login = applicationContext.getBean(loginTypeEnum.getStrategy(), UserLoginStrategy.class);
        Long userId = login.login(req);

        //创建token
        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(userId);

        UserInfoVO userInfo = new UserInfoVO();
        userInfo.setToken(authTokenComponent.createAccessToken(accessToken));
        userInfo.setAccount(userId);

        return userInfo;
    }

    @Override
    public UserRegisterInfoVO loginByPhone(UserLoginDTO req) {

        LoginTypeEnum loginTypeEnum = LoginTypeEnum.getLoginTypeEnum(req.getLoginScenes());
        UserLoginStrategy login = applicationContext.getBean(loginTypeEnum.getStrategy(), UserLoginStrategy.class);
        Long userId = login.login(req);

        UserRegisterInfoVO userRegisterInfoVO = new UserRegisterInfoVO();

        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(userId);
        userRegisterInfoVO.setToken(authTokenComponent.createAccessToken(accessToken));
        return userRegisterInfoVO;
    }
}
