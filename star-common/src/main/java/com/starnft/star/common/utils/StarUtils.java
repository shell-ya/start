package com.starnft.star.common.utils;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Tool 工具
 *
 * @author WeiChunLAI
 */
public class StarUtils {

    /**
     * 生成验证码
     *
     * @return
     */
    public static String getVerifyCode() {
        String code = null;
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            code = String.valueOf(secureRandom.nextInt(900000) + 1000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    /***
     *
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256Str(String str) {
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }
}
