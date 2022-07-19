package com.starnft.star.notice;

import cloud.tianai.captcha.spring.autoconfiguration.ImageCaptchaAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
//@EnableSwagger2
@EnableTransactionManagement
@EnableAspectJAutoProxy
@SpringBootApplication(exclude = {ImageCaptchaAutoConfiguration.class})
@Configurable
@MapperScan("com.starnft.star.infrastructure.mapper")
@ComponentScan(basePackages = {"com.starnft.star"})
public class NotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }
}
