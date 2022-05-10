package com.starnft.star.management.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.starnft.star.infrastructure.mapper")
@Configuration
public class MybatisConfiguration {
}
