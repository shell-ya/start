package com.starnft.star.infrastructure.util;

import com.alibaba.fastjson.JSONArray;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.json.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
public class JwtUtil {

    @Autowired
    RedisUtil redisUtil;

    @Value("${auth.single.online:false}")
    private Boolean single;

    @Value("${auth.token.timeout:30}")
    private Long timeout;

    /**
     * rsa token 解密
     */
    public void decryptToken(String token, String roleId) {
        //从redis获取token
        String tokenKey = "authToken:" + MD5Util.encryptMD5(token);
        boolean exists = redisUtil.exists(tokenKey);
        if (!exists) {
            log.error("token已失效，请重新登录:{}", token);
            throw new StarException(StarError.TOKEN_EXPIRED_ERROR);
        } else {
            String accountName = getAccountName(token);
            String accountNameKey = "authToken:" + accountName;
            if (single) {
                boolean accountExists = redisUtil.exists(accountNameKey);
                if (!accountExists) {
                    throw new StarException(StarError.TOKEN_EXPIRED_ERROR);
                }

                String redisToken = redisUtil.get(accountNameKey);
                log.info("前端token：[{}], redis-token:[{}]", token, redisToken);
                if (!token.equals(redisToken)) {
                    log.error("该账户已在其它地方登录:{}", accountName);
                    throw new StarException(StarError.ACCOUNT_LOGIN_OTHER_ERROR);
                }
            }
            //判断账户是否被禁用
            String keyUser = "disableUser:" + getAccountId(token);
            Boolean accountId = redisUtil.exists(keyUser);
            if (!accountId) {
                //判断角色是否被禁用
                String keyRole = "disableRole:" + roleId;
                Boolean roleIdExists = redisUtil.exists(keyRole);
                if (!roleIdExists) {
                    refresh(token);
                    disablePreToken(accountName, token);
                    return;
                } else {
                    throw new StarException(StarError.ROLE_IS_DISABLE_ERROR);
                }
            }
            redisUtil.delByKey(keyUser);
            redisUtil.delByKey(tokenKey);
            redisUtil.delByKey(accountNameKey);
            throw new StarException(StarError.TOKEN_EXPIRED_ERROR);
        }
    }

    /**
     * 解析token
     */
    public static JWT parseJwt(String token) {
        String[] split = token.split("\\.");
        byte[] decode = Base64.getUrlDecoder().decode(split[1]);
        return Mapper.deserialize(decode, JWT.class);
    }

    /**
     * 刷新token
     */
    public void refresh(String token) {
        redisUtil.set("authToken:" + MD5Util.encryptMD5(token), token, timeout, TimeUnit.MINUTES);
    }

    /**
     * 获取accountId
     *
     * @param token token
     * @return accountId
     */
    public String getAccountId(String token) {
        JWT jwt = parseJwt(token);
        //获取accountId
        return jwt.getAllClaims().get("accountId").toString();
    }

    /**
     * 获取accountId
     *
     * @param token token
     * @return accountId
     */
    public static String getAccountName(String token) {
        JWT jwt = parseJwt(token);
        //获取AccountName
        return jwt.getAllClaims().get("accountName").toString();
    }

    /**
     * 获取roleId
     *
     * @param token
     * @return
     */
    public static List<Long> getRoleIds(String token) {
        JWT jwt = parseJwt(token);
        return JSONArray.parseArray(jwt.getAllClaims().get("roleIds").toString(), Long.class);
    }

    public void disablePreToken(String accountName, String token) {
        if (single) {
            redisUtil.set("authToken:" + accountName, token, timeout, TimeUnit.MINUTES);
        }
    }
}