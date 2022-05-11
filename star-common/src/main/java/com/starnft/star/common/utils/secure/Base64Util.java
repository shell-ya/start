package com.starnft.star.common.utils.secure;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64Util
 * Base64工具类
 */
@Slf4j
public class Base64Util {

    private Base64Util() {}

    /**
     * Base64 编码
     *
     * @param text 明文
     * @return 密文
     */
    public static String encode(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64 解码
     *
     * @param ciphertext 密文
     * @return 明文
     */
    public static String decode(String ciphertext) {
        if (StringUtils.isBlank(ciphertext)) {
            return null;
        }
        final byte[] decode = Base64.getDecoder().decode(ciphertext);
        return new String(decode, StandardCharsets.UTF_8);
    }
}
