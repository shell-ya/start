package com.starnft.star.domain.support.ids.policy;

import cn.hutool.core.net.NetUtil;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.domain.support.ids.IIdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Ryan z
 * @description: 工具包下的雪花算法
 * @date: 2022/5/5
 */
@Component
public class SnowFlake implements IIdGenerator {

    private SnowflakeWorker snowflakeWorker;

    @PostConstruct
    public void init() {
        // 0 ~ 31 位，可以采用配置的方式使用
        long workerId;
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        } catch (Exception e) {
            workerId = NetUtil.getLocalhostStr().hashCode();
        }

        workerId = workerId >> 16 & 31;

        long dataCenterId = 1L;

        snowflakeWorker = new SnowflakeWorker(workerId, dataCenterId);
    }

    @Override
    public synchronized long nextId() {
        return snowflakeWorker.nextId();
    }

}
