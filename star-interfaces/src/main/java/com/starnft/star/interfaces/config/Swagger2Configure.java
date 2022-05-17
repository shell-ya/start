package com.starnft.star.interfaces.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @author WeiChunLAI
 * @date 2022/5/17 21:43
 */
@Configuration
@EnableSwagger2
public class Swagger2Configure {

    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("默认选项")
                .apiInfo(webApiInfo())
                .select()
                //withMethodAnnotation使用ApiOperation这个注解的接口，才会生成文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    //默认展示
    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("")
                .description("网站微服务接口定义")
                .version("1.0.0")
                .contact(new Contact("百度一下", "http://baidu.com", "1172114335@qq.com"))
                .build();
    }
}
