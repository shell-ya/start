package com.starnft.star.domain.sms.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sms-strategy")
public class StrategyConfigs {
    private Integer strategy;
}
