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

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.config.container.Channel;
import com.starnft.star.domain.payment.config.container.PayConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
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
        for (Channel channel : payConf.getChannels()) {
            if (channel.getVendor().equals(StarConstants.Pay_Vendor.SandPay.name())) {
                sdChannel = channel;
            }
        }
        if (sdChannel == null) {
            log.error("[{}] 加载PayConf配置为空： Channels = {}", this.getClass().getSimpleName(), payConf.getChannels());
            throw new RuntimeException("缺少PayConf配置信息！");
        }
        // 加载私钥
        initPulbicKey(sdChannel.getProperties().get("sandCertPath"));
        log.info("加载公钥成功");
        // 加载公钥
        initPrivateKey(sdChannel.getProperties().get("signCertPath"), sdChannel.getProperties().get("signCertPwd"));
        log.info("加载私钥成功");
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
