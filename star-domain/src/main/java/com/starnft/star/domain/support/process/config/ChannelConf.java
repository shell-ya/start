package com.starnft.star.domain.support.process.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "channel-configs")
public class ChannelConf implements Serializable {

    private List<TempConf> tempConfs;

}
