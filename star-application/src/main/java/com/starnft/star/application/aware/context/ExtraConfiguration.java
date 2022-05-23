package com.starnft.star.application.aware.context;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "extra-configuration")
public class ExtraConfiguration {

}
