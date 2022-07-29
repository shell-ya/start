package com.starnft.star.common.chain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@ConfigurationProperties(prefix = "tichain")
@PropertySource("classpath:tichain.properties")
public class ChainConfiguration {
    private String gateway;
    private String appId;
    private String appKey;
}
