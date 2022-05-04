package com.starnft.star.infrastructure;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Slf4j
@EnableSwagger2
@EnableAspectJAutoProxy
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class ArkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArkApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> log.info("Ark server started！");
    }
}