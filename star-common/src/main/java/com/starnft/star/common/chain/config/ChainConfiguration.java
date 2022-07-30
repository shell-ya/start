package com.starnft.star.common.chain.config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:tichain.properties")
public class ChainConfiguration {
    @Value("${tichain.gateway}")
    private String gateway;
    @Value("${tichain.app_id}")
    private String appId;
    @Value("${tichain.app_key}")
    private String appKey;
}
