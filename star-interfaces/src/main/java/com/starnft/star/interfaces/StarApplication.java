package com.starnft.star.interfaces;


import cn.dustlight.captcha.annotations.EnableCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Slf4j
//@EnableSwagger2
@EnableTransactionManagement
@EnableAspectJAutoProxy
@SpringBootApplication
@Configurable
@MapperScan("com.starnft.star.infrastructure.mapper")
@ComponentScan(basePackages = {"com.starnft.star"})
@EnableCaptcha
public class StarApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> log.info("Star server started！");
    }
}