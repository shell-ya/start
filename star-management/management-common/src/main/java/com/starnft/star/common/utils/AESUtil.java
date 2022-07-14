package com.starnft.star.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AESUtil
 * AES工具类
 *
 */
@Slf4j
public class AESUtil {

    private AESUtil() {}

    /**
     * 加密、解密方式
     */
    private static final String AES = "AES";

    /**
     * 初始向量值，必须16位
     */
    private static final String IV_STRING = "16-Bytes--String";

    /**
     * 默认秘钥，必须16位
     */
    private static final String DEFAULT_KEY = "70pQxrWV7NWgGRXQ";

    /**
     * 指定加密的算法、工作模式和填充方式
     */
    private static final String CIPHER = "AES/CBC/PKCS5Padding";

    /**
     * AES 使用默认秘钥加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String encrypt(String text) {
        return encrypt(text, DEFAULT_KEY);
    }

    /**
     * AES 自定义秘钥加密
     *
     * @param text 明文
     * @param key  秘钥（必须16位）
     * @return 密文
     */
    public static String encrypt(String text, String key) {
        if (StringUtils.isAnyBlank(text, key) || 16 != key.length()) {
            return null;
        }
        try {
            byte[] byteContent = text.getBytes(StandardCharsets.UTF_8);
            byte[] enCodeFormat = key.getBytes();
            // 注意，为了能与 iOS 统一这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, AES);
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(byteContent);
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * AES 默认秘钥解密
     *
     * @param ciphertext 密文
     * @return 明文
     */
    public static String decrypt(String ciphertext) {
        return decrypt(ciphertext, DEFAULT_KEY);
    }

    /**
     * AES 自定义秘钥解密
     *
     * @param ciphertext 密文
     * @param key        秘钥（必须16位）
     * @return 明文
     */
    public static String decrypt(String ciphertext, String key) {
        if (StringUtils.isAnyBlank(ciphertext, key) || 16 != key.length()) {
            return null;
        }
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(ciphertext);
            byte[] enCodeFormat = key.getBytes();
            SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, AES);
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] result = cipher.doFinal(encryptedBytes);
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
