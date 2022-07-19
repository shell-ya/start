package com.starnft.star.notice.config;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableMethodCache(basePackages = "com.starnft.star")
@EnableCreateCacheAnnotation
//@EnableScheduling
@PropertySource("classpath:mq-info-config.properties")
public class BeanConfig {

}
