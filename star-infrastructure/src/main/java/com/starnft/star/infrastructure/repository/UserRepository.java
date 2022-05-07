package com.starnft.star.infrastructure.repository;

import cn.hutool.crypto.digest.MD5;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.component.AuthTokenComponent;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.po.AccessToken;
import com.starnft.star.common.utils.JwtUtil;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.domain.model.dto.UserLoginDTO;
import com.starnft.star.domain.model.vo.UserInfoVO;
import com.starnft.star.domain.model.vo.UserRegisterInfoVO;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.infrastructure.entity.user.UserInfoEntity;
import com.starnft.star.infrastructure.enums.LoginTypeEnum;
import com.starnft.star.infrastructure.mapper.UserInfoMapper;
import com.starnft.star.infrastructure.repository.user.strategy.UserLoginStrategy;
import com.starnft.star.infrastructure.util.RedisUtil;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Service
public class UserRepository implements IUserRepository {

    @Autowired
    private UserInfoMapper userInfoMapper;

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
