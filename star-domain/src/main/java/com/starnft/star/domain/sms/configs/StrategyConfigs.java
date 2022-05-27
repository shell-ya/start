package com.starnft.star.domain.sms.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "strategy")
@PropertySource("classpath:sms/strategy.yml")
public class StrategyConfigs {
    private Integer sms;
}
