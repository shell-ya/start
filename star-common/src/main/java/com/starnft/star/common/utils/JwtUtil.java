package com.starnft.star.common.utils;


import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import io.fusionauth.jwt.JWTExpiredException;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.json.Mapper;
import io.fusionauth.jwt.rsa.RSASigner;
import io.fusionauth.jwt.rsa.RSAVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.util.Base64;


@Slf4j
@Component
public class JwtUtil {


    private  String privateKey = "/pem/rsa_private_key_2048.pem";

    private  String pubicKey = "/pem/rsa_public_key_2048.pem";

    /**
     * 解析token
     */
    public static JWT parseJwt(String token) {
        String[] split = token.split("\\.");
        byte[] decode = Base64.getUrlDecoder().decode(split[1]);
        return Mapper.deserialize(decode, JWT.class);
    }

    /**
     * 获取userId
     *
     * @param token token
     * @return userId
     */
    public static String getAccountId(String token) {
        JWT jwt = parseJwt(token);
        //获取AccountName
        return jwt.getAllClaims().get("userId").toString();
    }


    /**
     * rsa 加密生成 token
     */
    public  String encryptToken(JWT jwt) {
        try {
            Signer signer = RSASigner.newSHA256Signer(
                    StreamUtils.copyToString(new ClassPathResource(privateKey).getInputStream(), Charset.defaultCharset())
            );
            return JWT.getEncoder().encode(jwt, signer);
        } catch (Exception e) {
            log.error("[JwtUtil.encryptToken] jwt encryptToken error", e);
            throw new StarException(StarError.ACCOUNT_TOKEN_DECRYPT_ERROR);
        }
    }

    /**
     * rsa token 解密
     */
    public JWT decryptToken(String token) {
        try {
            Verifier verifier = RSAVerifier.newVerifier(
                    new ClassPathResource(pubicKey).getFile().toPath());
            return JWT.getDecoder().decode(token, verifier);
        } catch (JWTExpiredException ex) {
            log.warn("[JwtUtil.encryptToken] token is expired", ex);
            throw new StarException(StarError.ACCOUNT_TOKEN_DECRYPT_ERROR);
        } catch (Exception e) {
            log.error("[JwtUtil.encryptToken] jwt decryptToken error", e);
            throw new StarException(StarError.ACCOUNT_TOKEN_DECRYPT_ERROR);
        }
    }

}