package com.starnft.star.common.component;


import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.po.AccessToken;
import com.starnft.star.common.utils.JwtUtil;
import io.fusionauth.jwt.domain.JWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;


/**
 * @author WeiChunLAI
 */
@Slf4j
@Component
public class AuthTokenComponent {

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 令牌过期时间 此处单位/小时
     */
    private Long tokenExpireHour = 12L;


    /**
     * 生成token
     * @param accessTokenBo
     * @return
     */
    public String createAccessToken(AccessToken accessTokenBo) {
        ZonedDateTime tokenZdt = ZonedDateTime.now(ZoneOffset.UTC).plusHours(tokenExpireHour);
        JWT accessToken = new JWT()
                .setIssuer(StarConstants.SERVICE_NAME)
                .setExpiration(tokenZdt)
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .addClaim(StarConstants.PHONE , accessTokenBo.getPhone())
                .addClaim(StarConstants.USER_ID, accessTokenBo.getUserId());
        return jwtUtil.encryptToken(accessToken);
    }

}
