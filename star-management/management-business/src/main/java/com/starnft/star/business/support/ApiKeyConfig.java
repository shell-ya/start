package com.starnft.star.business.support;

import com.starnft.star.business.service.IDictionaryRepository;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/6/22 11:01 AM
 * @Author ： shellya
 */
@Configuration
public class ApiKeyConfig {

    private static final String categoryCode = "APIKEY";

    private final static Map<String, ApiKeyInfo> keys = new HashMap<>();

    @Resource
    private IDictionaryRepository dictionaryRepository;

    @PostConstruct
    public void initApiKey() {
        ApiKeyInfo apiKeyInfo = dictionaryRepository.getDictCodes(categoryCode, ApiKeyInfo.class);
        keys.put(categoryCode, apiKeyInfo);
    }

    public static String getAppId() {
        return keys.get(categoryCode).getAppId();
    }

    public static String getSecretId() {
        return keys.get(categoryCode).getSecretId();
    }

    public static String getSecretKey() {
        return keys.get(categoryCode).getSecretKey();
    }

}
