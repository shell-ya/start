package com.starnft.star.domain.apk.impl;

import com.starnft.star.domain.apk.ApkConfig;
import com.starnft.star.domain.apk.IApkInfoObtain;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Date 2022/7/16 10:02 PM
 * @Author ： shellya
 */
@Component
public class ApkInfoObtain extends ApkConfig implements IApkInfoObtain {

    @Override
    public String getUrl(Integer phoneModel) {
        String url = 0 == phoneModel ? android() : ios();
        if (Objects.isNull(url)) url = "https://itunes.apple.com/cn/app/id1634042904?mt=8";
        return url;
    }
}
