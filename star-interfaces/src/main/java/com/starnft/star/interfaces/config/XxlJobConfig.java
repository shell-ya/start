package com.starnft.star.interfaces.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
public class XxlJobConfig implements EnvironmentAware {

    private static Logger log = LoggerFactory.getLogger(XxlJobConfig.class);
    private Environment environment;


    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(environment.getProperty("xxl.job.admin.addresses"));
        xxlJobSpringExecutor.setAppname(environment.getProperty("xxl.job.executor.appname"));
        xxlJobSpringExecutor.setIp(environment.getProperty("xxl.job.executor.ip"));
        xxlJobSpringExecutor.setPort(Integer.parseInt(Objects.requireNonNull(environment.getProperty("xxl.job.executor.port"))));
        xxlJobSpringExecutor.setAccessToken(environment.getProperty("xxl.job.accessToken"));
        xxlJobSpringExecutor.setLogPath(environment.getProperty("xxl.job.executor.logpath"));
        xxlJobSpringExecutor.setLogRetentionDays(Integer.parseInt(Objects.requireNonNull(environment.getProperty("xxl.job.executor.logretentiondays"))));
        return xxlJobSpringExecutor;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
