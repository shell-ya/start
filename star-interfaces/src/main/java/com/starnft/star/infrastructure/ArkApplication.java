package com.starnft.star.infrastructure;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Slf4j
//@EnableSwagger2
@EnableAspectJAutoProxy
@SpringBootApplication
@Configurable
@MapperScan("com.starnft.star.infrastructure.mapper")
@ComponentScan(basePackages = {"com.starnft.star"})
public class ArkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArkApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> log.info("Ark server started！");
    }
}