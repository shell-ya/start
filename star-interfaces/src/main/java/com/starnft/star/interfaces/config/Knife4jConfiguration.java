package com.starnft.star.interfaces.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
@Slf4j
public class Knife4jConfiguration {
    @Value("${swagger.packages}")
    private  String packages;

    /** 是否开启swagger */
    @Value("${swagger.enabled}")
    private boolean enabled;

    @Bean
    public Docket createRestApi() {
         log.info("---------{}----------",packages);
        Predicate<RequestHandler> requestHandlerPredicate = RequestHandlerSelectors.basePackage(packages);
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enabled)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(requestHandlerPredicate)
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description("∞big")
                .contact(new Contact("", " ", " "))
                .version("v3.0.0")
                .title("在线Api文档")
                .build();
    }


}
