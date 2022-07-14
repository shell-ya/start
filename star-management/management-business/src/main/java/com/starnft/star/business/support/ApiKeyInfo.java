package com.starnft.star.business.support;

/**
 * @Date 2022/6/22 11:02 AM
 * @Author ： shellya
 */
public class ApiKeyInfo {
    private String appId;

    private String secretId;

    private String secretKey;

    public ApiKeyInfo() {
    }

    public ApiKeyInfo(String appId, String secretId, String secretKey) {
        this.appId = appId;
        this.secretId = secretId;
        this.secretKey = secretKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
