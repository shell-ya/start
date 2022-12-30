package com.starnft.star.interfaces.controller.trans;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Spring 异步配置
 *
 * @author: xuan.zhou
 * @date: 2021/12/29
 */
@Slf4j
@EnableAsync
@Configuration
public class SpringAsyncConfig implements AsyncConfigurer {

    /**
     * 异步任务执行线程池
     *
     * @return
     */
    @Bean(name = "asyncExecutor")
    public ThreadPoolTaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(4);
        //配置最大线程数
        executor.setMaxPoolSize(10);
        //允许线程空闲时间（单位：秒）
        executor.setKeepAliveSeconds(180);
        //配置队列大小
        executor.setQueueCapacity(200);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-task-");
        // 设置拒绝策略：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 配置适配器 MdcTaskDecorator
        executor.setTaskDecorator(new MdcTaskDecorator());

        //执行初始化
        executor.initialize();
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return this.asyncExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("执行异步方法出错：method={}, params={}, message={}", method.getName(), JSONUtil.toJsonStr(objects), throwable.getMessage(), throwable);
        };
    }
}
