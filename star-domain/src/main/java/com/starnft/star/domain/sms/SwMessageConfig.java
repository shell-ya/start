package com.starnft.star.domain.sms;

import com.starnft.star.domain.support.key.repo.IDictionaryRepository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class SwMessageConfig {
    private static final String categoryCode = "swMessage";
    private final static Map<String, SwMessageInfo> keys = new HashMap<>();
    @Resource
    private IDictionaryRepository dictionaryRepository;
    @PostConstruct
    public void initApiKey() {
        SwMessageInfo apiKeyInfo = dictionaryRepository.getDictCodes(categoryCode,SwMessageInfo.class);
        keys.put(categoryCode, apiKeyInfo);
    }
    public static String getSwMessageApi() {
        return keys.get(categoryCode).getSwMessageApi();
    }
    public static String getSwMessageAppSecret() {
        return keys.get(categoryCode).getSwMessageAppSecret();
    }
    public static String getSwMessageAppKey() {
        return keys.get(categoryCode).getSwMessageAppKey();
    }
    public static String getSwMessageHeader() {
        return keys.get(categoryCode).getSwMessageHeader();
    }
    public static String getSwMessageAppCode() {
        return keys.get(categoryCode).getSwMessageAppCode();
    }
}
