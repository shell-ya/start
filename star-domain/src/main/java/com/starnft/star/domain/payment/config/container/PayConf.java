package com.starnft.star.domain.payment.config.container;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "pay-conf")
public class PayConf implements Serializable {

    private List<Channel> channels;

}
