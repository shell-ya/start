package com.starnft.star.domain.support.key;

import com.starnft.star.domain.support.key.repo.IDictionaryRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
