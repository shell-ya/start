package com.starnft.star.interfaces.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Objects;

@Configuration
@EnableConfigurationProperties
public class YamlConfigurerConfiguration {

    private static final String path = "/preconfig/pre_config.yaml";
    private static final String pay_conf_path = "/preconfig/pay_conf.yaml";
    private static final String channel_conf_path = "/preconfig/channel_conf.yaml";

    @Bean
    @SneakyThrows
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        ClassPathResource classPathResource = new ClassPathResource(path);
        ClassPathResource payConfClassPathResource = new ClassPathResource(pay_conf_path);
        ClassPathResource channelConfClassPathResource = new ClassPathResource(channel_conf_path);
        //自定义yaml文件的存储路径
        yaml.setResources(classPathResource, payConfClassPathResource, channelConfClassPathResource);
        configurer.setProperties(Objects.requireNonNull(yaml.getObject()));
        return configurer;
    }
}
