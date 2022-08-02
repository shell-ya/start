package com.starnft.star.domain.captcha.config;

import com.starnft.star.domain.support.key.repo.IDictionaryRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Harlan
 * @date 2022/07/27 16:22
 */
@Slf4j
@Configuration
public class NetEaseCaptchaConfig {

    private static final String CATEGORY_CODE = "Netease_Captcha";
    private static final Map<String, ConfigItem> CONFIG_ITEM_MAP = new HashMap<>();

    @Resource
    private IDictionaryRepository dictionaryRepository;

    @PostConstruct
    public void initConfig() {
        CONFIG_ITEM_MAP.put(CATEGORY_CODE, dictionaryRepository.getDictCodes(CATEGORY_CODE, ConfigItem.class));
    }

    public static String getVerifyApi() {
        return CONFIG_ITEM_MAP.get(CATEGORY_CODE).verifyApi;
    }

    public static String getCaptchaId() {
        return CONFIG_ITEM_MAP.get(CATEGORY_CODE).captchaId;
    }

    public static String getSecretId() {
        return CONFIG_ITEM_MAP.get(CATEGORY_CODE).secretId;
    }

    public static String getSecretKey() {
        return CONFIG_ITEM_MAP.get(CATEGORY_CODE).secretKey;
    }

    @Data
    public static class ConfigItem {
        private String verifyApi;
        private String captchaId;
        private String secretId;
        private String secretKey;
    }
}
