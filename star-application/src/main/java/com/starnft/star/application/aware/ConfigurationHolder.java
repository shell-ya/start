package com.starnft.star.application.aware;

import com.starnft.star.common.exception.StarException;
import com.starnft.star.application.aware.context.ExtraConfiguration;
import com.starnft.star.application.aware.config.PaymentConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * @author Ryan Z / haoran
 * @description 获取yml文件中的配置
 * @date  2022/5/17
 */
@Component
public class ConfigurationHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static ExtraConfiguration configuration;

    @PostConstruct
    private void initConfig() {
        configuration = applicationContext.getBean(ExtraConfiguration.class);
    }

    public static PaymentConfig getPayConfig() {
        if (null == configuration) {
            throw new StarException("[ExtraConfiguration] may not be loaded!");
        }
        if (null == configuration.getPayConfig()) {
            throw new StarException("[payConfig] may not be loaded!");
        }
        return configuration.getPayConfig();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigurationHolder.applicationContext = applicationContext;
    }

}
