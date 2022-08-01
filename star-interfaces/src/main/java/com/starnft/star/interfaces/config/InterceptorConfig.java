package com.starnft.star.interfaces.config;

import com.starnft.star.interfaces.interceptor.StatInterceptor;
import com.starnft.star.interfaces.interceptor.UserResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WeiChunLAI
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private StatInterceptor statInterceptor;

    @Resource
    private UserResolver userResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(statInterceptor);
        interceptorRegistration.addPathPatterns("/**");
        //不拦截路径
        List<String> irs = new ArrayList<String>();
        irs.add("/api/*");
        irs.add("/doc.html");
        irs.add("/service-worker.js");
        irs.add("/swagger-resources");
        irs.add("/webjars/**");
        irs.add("/swagger-ui.html");
        interceptorRegistration.excludePathPatterns(irs);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }
}
