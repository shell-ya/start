package com.starnft.star.management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configurable
@EnableAspectJAutoProxy
//@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = {"com.starnft.star"})
public class StarManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarManagementApplication.class, args);
    }

}
