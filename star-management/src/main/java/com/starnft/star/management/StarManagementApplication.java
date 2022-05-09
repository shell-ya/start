package com.starnft.star.management;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@Configurable
@EnableSwagger2
@SpringBootApplication
public class StarManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarManagementApplication.class, args);
    }

}
