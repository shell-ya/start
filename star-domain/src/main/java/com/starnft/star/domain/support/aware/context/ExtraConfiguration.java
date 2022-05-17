package com.starnft.star.domain.support.aware.context;

import com.starnft.star.domain.support.aware.config.PaymentConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "extra-configuration")
public class ExtraConfiguration {

    private PaymentConfig payConfig;
}
