package com.starnft.star.application.aware;

import com.starnft.star.application.aware.context.ExtraConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * @author Ryan Z / haoran
 * @description 获取yml文件中的配置 加载编排领域服务时候需要的配置
 *              若在领域层直接使用实体类标注@ConfigurationProperties(prefix = "")来加载yml配置中的数据
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
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigurationHolder.applicationContext = applicationContext;
    }

}
