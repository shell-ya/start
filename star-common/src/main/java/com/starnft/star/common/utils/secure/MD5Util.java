package com.starnft.star.common.utils.secure;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5Util
 * MD5工具类
 *
 */
@Slf4j
public class MD5Util {

    private MD5Util() {}

    /**
     * 加密、解密方式
     */
    private static final String MD5 = "MD5";

    /**
     * MD5 加密方法
     */
    public static String encrypt(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        /*
        char hexs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] bytes = text.getBytes(ENCODEING);
            MessageDigest messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.update(bytes);
            byte[] md = messageDigest.digest();
            int j = md.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                chars[k++] = hexs[byte0 >>> 4 & 0xf];
                chars[k++] = hexs[byte0 & 0xf];
            }
            return String.valueOf(chars);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
        */
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(MD5);
            byte[] dataBytes = text.getBytes(StandardCharsets.UTF_8);
            messageDigest.update(dataBytes);
            return new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
