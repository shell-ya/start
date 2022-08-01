package com.starnft.star.common.utils;

import org.apache.commons.codec.binary.Hex;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.NumberFormat;

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
            byte[] hash = messageDigest.digest(str.getBytes(StandardCharsets.UTF_8));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }


    public static String formatMoney(BigDecimal money) {
        NumberFormat number = NumberFormat.getNumberInstance();
        number.setMinimumIntegerDigits(4);
        return money.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();
    }

//    public static void main(String[] args) {
//        System.out.println(StarUtils.getSHA256Str("654321"));
//    }
}
