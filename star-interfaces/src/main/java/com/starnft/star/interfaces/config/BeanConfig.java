package com.starnft.star.interfaces.config;

import cn.dustlight.captcha.annotations.EnableCaptcha;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableCaptcha
@EnableMethodCache(basePackages = "com.starnft.star")
@EnableCreateCacheAnnotation
@EnableScheduling
public class BeanConfig {

}
