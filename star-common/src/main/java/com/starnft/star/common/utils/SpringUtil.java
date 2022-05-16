package com.starnft.star.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringUtil
 * Spting上下文工具类，可通过该类获取容器bean
 */
@Component
@Slf4j
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (null == SpringUtil.applicationContext) {
            SpringUtil.applicationContext = applicationContext;
        }
        log.info("ApplicationContext配置成功，在普通类可以通过调用 SpringUtil.getAppContext() 获取 applicationContext 对象，applicationContext = " + SpringUtil.applicationContext);
    }

    /**
     * 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取bean
     *
     * @param name 名称
     */
    public static Object getBean(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过clazz获取bean
     *
     * @param clazz 类型
     */
    public static <T> T getBean(Class<T> clazz) {
        if (ObjectUtils.isEmpty(clazz)) {
            return null;
        }
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name及clazz获取bean
     *
     * @param name  名称
     * @param clazz 类型
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        if (StringUtils.isBlank(name) || ObjectUtils.isEmpty(clazz)) {
            return null;
        }
        return getApplicationContext().getBean(name, clazz);
    }
}
