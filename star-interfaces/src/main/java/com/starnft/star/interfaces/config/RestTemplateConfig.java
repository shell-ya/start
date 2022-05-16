package com.starnft.star.interfaces.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {

        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();

        int timeout = 60000;
        factory.setConnectTimeout(timeout);
        factory.setReadTimeout(timeout);
        factory.setWriteTimeout(timeout);

        return new RestTemplate(factory);
    }
}
