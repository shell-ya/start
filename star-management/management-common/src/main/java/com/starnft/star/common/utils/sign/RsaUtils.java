package com.starnft.star.common.utils.sign;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Date 2022/7/18 9:43 AM
 * @Author ： shellya
 */
public class RsaUtils {
    // Rsa 私钥
    public static String privateKey =
            "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAO0Rmzyz5izkoh0G\n" +
            "nb3+RvH8o4SeaV+g5Iqb+Ri84N7U8xlsU6OugjkSTZ79b6Lu8obsSUg78uY/XXZ7\n" +
            "LN6KW6j2sG7y/HpJ5Y1LJa/uyivH5clC+KHXQ8J8p7sojOoSAR+k6jyBkHwWmzzK\n" +
            "4Azutveq+DY1I1nFh8d3eE3fOuR/AgMBAAECgYAXvKWQhGrNn+7jfSAaP/WWkTdG\n" +
            "Nh5S5uc7QbbFVVQmu6fDqecOApEjacO/4Zxl8grym4IS6328tCeFj3vEpJHcvcuN\n" +
            "GK2ZyZ4OVDxj5PypOBBLJfNMrENp3mEG/yRxwBY8x+qncNAqEj4pJXo2c+Wwj6Sv\n" +
            "STmC7xytT0TiUn9owQJBAP3Q1JhXJ6vw5hksgKYKgL8Ak1jeioXOaGUBwI217h3Z\n" +
            "/GVo2G63d+ZrIJqJ0WAGjzutib122QijcFt5y/4gpVECQQDvG+Go9KftyAZf0vKG\n" +
            "6bctqgU3pCvHHeF5TPoNUwJUv5COGUCrysgD3FXJDofIk/0tJ+qUMyr2zTp57b93\n" +
            "E7jPAkAOKb4DNjHPhWTBmNsg6MsgXV7bgaeHKepnYH6nKbS807Ii0oPpQbGeGPI7\n" +
            "zV7ylcseUGO0P9KCieQ2Joi8iIPRAkBS5nXMoFaAqHMdUgPoJqvt1x7L9c1/0apU\n" +
            "5g1kBT2vqGM0ASlc5oeGXyQW+0S0lCqZN1erXjotK2Z006soW9wdAkARppJWlxFS\n" +
            "Zi2BV5Db6ahx9csMDEjopXSB+5ol/w8uP4a1QbTag6SFFZv+EbpWvSvZMKFgZutR\n" +
            "EWhIo619C0OV\n";






    /**
     * 私钥解密
     *
     * @param privateKeyString 私钥
     * @param text             待解密的文本
     * @return 解密后的文本
     */
    public static String decryptByPrivateKey(String text) throws Exception {
        return decryptByPrivateKey(privateKey, text);
    }

    /**
     * 公钥解密
     *
     * @param publicKeyString 公钥
     * @param text            待解密的信息
     * @return 解密后的文本
     */
    public static String decryptByPublicKey(String publicKeyString, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * 私钥加密
     *
     * @param privateKeyString 私钥
     * @param text             待加密的信息
     * @return 加密后的文本
     */
    public static String encryptByPrivateKey(String privateKeyString, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * 私钥解密
     *
     * @param privateKeyString 私钥
     * @param text             待解密的文本
     * @return 解密后的文本
     */
    public static String decryptByPrivateKey(String privateKeyString, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * 公钥加密
     *
     * @param publicKeyString 公钥
     * @param text            待加密的文本
     * @return 加密后的文本
     */
    public static String encryptByPublicKey(String publicKeyString, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * 构建RSA密钥对
     *
     * @return 生成后的公私钥信息
     */
    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
        String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        return new RsaKeyPair(publicKeyString, privateKeyString);
    }

    /**
     * RSA密钥对对象
     */
    public static class RsaKeyPair {
        private final String publicKey;
        private final String privateKey;

        public RsaKeyPair(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }
    }
}
