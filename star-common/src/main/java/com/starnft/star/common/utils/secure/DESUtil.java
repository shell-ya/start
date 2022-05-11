package com.starnft.star.common.utils.secure;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * DESUtil
 * DES工具类
 *
 */
@Slf4j
public class DESUtil {

    private DESUtil() {}

    /**
     * 加密解密方式
     */
    private static final String DES = "DES";

    /**
     * 秘钥
     */
    private static final String DEFAULT_KEY = "https://github.com/micyo202";

    /**
     * DES 使用默认秘钥加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String encrypt(String text) {
        return encrypt(text, DEFAULT_KEY);
    }

    /**
     * DES 使用自定义秘钥加密
     *
     * @param text 明文
     * @param key  秘钥
     * @return 密文
     */
    public static String encrypt(String text, String key) {
        if (StringUtils.isAnyBlank(text, key)) {
            return null;
        }
        try {
            // 生成一个可信任的随机数源
            SecureRandom secureRandom = new SecureRandom();
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec keySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(keySpec);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(DES);
            // 用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, secureRandom);
            byte[] bytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * DES 使用默认秘钥解密
     *
     * @param ciphertext 密文
     * @return 明文
     */
    public static String decrypt(String ciphertext) {
        return decrypt(ciphertext, DEFAULT_KEY);
    }

    /**
     * DES 使用自定义秘钥解密
     *
     * @param ciphertext 密文
     * @param key        秘钥
     * @return 明文
     */
    public static String decrypt(String ciphertext, String key) {
        if (StringUtils.isAnyBlank(ciphertext, key)) {
            return null;
        }
        try {

            // 生成一个可信任的随机数源
            SecureRandom secureRandom = new SecureRandom();
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec keySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(keySpec);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(DES);
            // 用密钥初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, secureRandom);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext.getBytes(StandardCharsets.UTF_8)));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
