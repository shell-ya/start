package com.starnft.star.business.support.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "sw")
@PropertySource("classpath:sw_message.properties")
public class SwMessageConfig {
    private String  swMessageAppKey;
    private String  swMessageHeader;
    private String  swMessageAppCode;
    private String  swMessageApi;
    private String  swMessageAppSecret;
}
