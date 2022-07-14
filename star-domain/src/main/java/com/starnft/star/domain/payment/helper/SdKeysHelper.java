/**
 * Licensed Property to Sand Co., Ltd.
 * <p>
 * (C) Copyright of Sand Co., Ltd. 2010
 * All Rights Reserved.
 * <p>
 * <p>
 * Modification History:
 * =============================================================================
 * Author           Date           Description
 * ------------ ---------- ---------------------------------------------------
 * 企业产品团队       2016-10-12       证书工具类.
 * =============================================================================
 */
package com.starnft.star.domain.payment.helper;
//支付宝生活号/支付宝小程序

import cn.hutool.core.collection.CollectionUtil;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.config.container.Channel;
import com.starnft.star.domain.payment.config.container.PayConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @version 2.0.0
 * @ClassName:
 * @Description: sdk证书工具类，主要用于对证书的加载和使用
 */
@Slf4j
@Component
public class SdKeysHelper {
    public final String PUBLIC_KEY = "public_key";
    public final String PRIVATE_KEY = "private_key";
    private final ConcurrentHashMap<String, Object> keys = new ConcurrentHashMap<String, Object>();

    public SdKeysHelper(PayConf payConf) throws Exception {
        Channel sdChannel = null;
        if (Objects.isNull(payConf) || CollectionUtil.isEmpty(payConf.getChannels())) {
            return;
        }
        for (Channel channel : payConf.getChannels()) {
            if (channel.getVendor().equals(StarConstants.Pay_Vendor.SandPay.name())) {
                sdChannel = channel;
            }
        }

        // 加载私钥
        initPulbicKey(sdChannel.getProperties().get("sandCertPath"));
        log.info("加载公钥成功");
        // 加载公钥
        initPrivateKey(sdChannel.getProperties().get("signCertPath"), sdChannel.getProperties().get("signCertPwd"));
        log.info("加载私钥成功");
    }

    public boolean verifyDigitalSign(byte[] plainBytes, byte[] signBytes, PublicKey publicKey, String signAlgorithm) throws Exception {
        boolean isValid = false;
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initVerify(publicKey);
            signature.update(plainBytes);
            isValid = signature.verify(signBytes);
            return isValid;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(String.format("验证数字签名时没有[%s]此类算法", signAlgorithm), e);
        } catch (InvalidKeyException e) {
            throw new Exception("验证数字签名时公钥无效", e);
        } catch (SignatureException e) {
            throw new Exception("验证数字签名时出现异常", e);
        }
    }

    public byte[] digitalSign(byte[] plainBytes, PrivateKey privateKey, String signAlgorithm) {
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initSign(privateKey);
            signature.update(plainBytes);
            byte[] signBytes = signature.sign();

            return signBytes;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(String.format("数字签名时没有[%s]此类算法", signAlgorithm));
        } catch (InvalidKeyException e) {
            throw new RuntimeException("数字签名时私钥无效", e);
        } catch (SignatureException e) {
            throw new RuntimeException("数字签名时出现异常", e);
        }
    }

    public PublicKey getPublicKey() {
        return (PublicKey) keys.get(PUBLIC_KEY);
    }

    public PrivateKey getPrivateKey() {
        return (PrivateKey) keys.get(PRIVATE_KEY);
    }

    private void initPulbicKey(String publicKeyPath) throws Exception {

        String classpathKey = "classpath:";
        if (publicKeyPath != null) {
            try {
                InputStream inputStream = null;
                if (publicKeyPath.startsWith(classpathKey)) {
                    inputStream = SdKeysHelper.class.getClassLoader().getResourceAsStream(publicKeyPath.substring(classpathKey.length()));
                } else {
                    inputStream = new FileInputStream(publicKeyPath);
                }
                PublicKey publicKey = getPublicKey(inputStream);
                keys.put(PUBLIC_KEY, publicKey);
            } catch (Exception e) {
                log.error("无法加载公钥[{}]", new Object[]{publicKeyPath});
                log.error(e.getMessage(), e);
                throw e;
            }
        }
    }

    private void initPrivateKey(String privateKeyPath, String keyPassword) throws Exception {

        String classpathKey = "classpath:";

        try {
            InputStream inputStream = null;
            if (privateKeyPath.startsWith(classpathKey)) {
                inputStream = SdKeysHelper.class.getClassLoader()
                        .getResourceAsStream(privateKeyPath.substring(classpathKey.length()));
            } else {
                inputStream = new FileInputStream(privateKeyPath);
            }
            PrivateKey privateKey = getPrivateKey(inputStream, keyPassword);
            keys.put(PRIVATE_KEY, privateKey);
        } catch (Exception e) {
            log.error("无法加载本地私银[" + privateKeyPath + "]");
            log.error(e.getMessage(), e);
            throw e;
        }
    }


    public PublicKey getPublicKey(InputStream inputStream) throws Exception {
        try {

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate oCert = (X509Certificate) cf.generateCertificate(inputStream);
            PublicKey publicKey = oCert.getPublicKey();
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("读取公钥异常");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("出现错误", e);
            }
        }
    }


    /**
     * 获取私钥对象
     *
     * @param inputStream 私钥输入流
     *                    <p>
     *                    密钥算法
     * @return 私钥对象
     * @throws Exception
     */
    public PrivateKey getPrivateKey(InputStream inputStream, String password) throws Exception {
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            char[] nPassword = null;
            if ((password == null) || password.trim().equals("")) {
                nPassword = null;
            } else {
                nPassword = password.toCharArray();
            }

            ks.load(inputStream, nPassword);
            Enumeration<String> enumas = ks.aliases();
            String keyAlias = null;
            if (enumas.hasMoreElements()) {
                keyAlias = (String) enumas.nextElement();
            }

            PrivateKey privateKey = (PrivateKey) ks.getKey(keyAlias, nPassword);
            return privateKey;
        } catch (FileNotFoundException e) {
            throw new Exception("私钥路径文件不存在");
        } catch (IOException e) {
            throw new Exception(e);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("生成私钥对象异常");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
            }
        }
    }

}
