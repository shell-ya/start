package com.starnft.star.infrastructure.config;

import com.starnft.star.infrastructure.interceptor.StatInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author WeiChunLAI
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private StatInterceptor statInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(statInterceptor);
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns("star/v1/login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }
}
