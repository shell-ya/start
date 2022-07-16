package com.starnft.star.domain.apk;

import com.starnft.star.domain.support.key.repo.IDictionaryRepository;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/7/16 9:56 PM
 * @Author ： shellya
 */
@Configuration
public class ApkConfig {
    private static final String categoryCode = "APK";
    private static final Map<String,ApkInfo> apk = new HashMap<>();

    @Resource
    private IDictionaryRepository dictionaryRepository;

    @PostConstruct
    public void initApkInfo(){
        ApkInfo apkInfo = dictionaryRepository.getDictCodes(categoryCode, ApkInfo.class);
        apk.put(categoryCode,apkInfo);
    }

    public static String getDownloadUrl(){
        return apk.get(categoryCode).getApkDownloadUrl();
    }
}
