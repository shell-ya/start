package com.starnft.star.common.utils.secure;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * SHAUtil
 * SHA哈希散列工具类
 *
 */
@Slf4j
public class SHAUtil {

    private SHAUtil(){}

    /**
     * 加密、解密方式：SHA-1、SHA-224、SHA-256、SHA-384、SHA-512
     */
    private static final String SHA_1 = "SHA-1";
    private static final String SHA_224 = "SHA-224";
    private static final String SHA_256 = "SHA-256";
    private static final String SHA_384 = "SHA-384";
    private static final String SHA_512 = "SHA-512";

    /**
     * SHA1 加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String encrypt(String text) {
        return encrypt(text, SHA_1);
    }

    /**
     * SHA1 加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String encrypt224(String text) {
        return encrypt(text, SHA_224);
    }

    /**
     * SHA1 加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String encrypt256(String text) {
        return encrypt(text, SHA_256);
    }

    /**
     * SHA1 加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String encrypt384(String text) {
        return encrypt(text, SHA_384);
    }

    /**
     * SHA1 加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String encrypt512(String text) {
        return encrypt(text, SHA_512);
    }

    /**
     * SHA 通用加密算法
     *
     * @param text      明文
     * @param algorithm 加密类型
     * @return 密文
     */
    private static String encrypt(String text, String algorithm) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(text.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = messageDigest.digest();
            return bytes2Str(bytes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 字节转字符串
     *
     * @param bytes 字节
     * @return 字符串
     */
    private static String bytes2Str(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String str;
        for (int i = 0; i < bytes.length; i++) {
            str = Integer.toHexString(bytes[i] & 0xFF);
            if (str.length() == 1) {
                // 1得到一位的进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }
}
