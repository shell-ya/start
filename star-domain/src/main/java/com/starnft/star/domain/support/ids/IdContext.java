package com.starnft.star.domain.support.ids;

import com.starnft.star.common.StarConstants;
import com.starnft.star.domain.support.ids.policy.RandomNumeric;
import com.starnft.star.domain.support.ids.policy.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan z
 * @description: Id 策略模式上下文配置「在正式的完整的系统架构中，ID 的生成会有单独的服务来完成，其他服务来调用 ID 生成接口即可」
 * @date: 2022/5/5
 */
@Configuration
public class IdContext {

    /**
     * 创建 ID 生成策略对象，属于策略设计模式的使用方式
     *
     * @param snowFlake 雪花算法，长码，大量
     * @param randomNumeric 随机算法，短码，大量，全局唯一需要自己保证
     * @return IIdGenerator 实现类
     */
    @Bean
    public Map<StarConstants.Ids, IIdGenerator> idGenerator(SnowFlake snowFlake, RandomNumeric randomNumeric) {
        Map<StarConstants.Ids, IIdGenerator> idGeneratorMap = new HashMap<>(8);
        idGeneratorMap.put(StarConstants.Ids.SnowFlake, snowFlake);
        idGeneratorMap.put(StarConstants.Ids.RandomNumeric, randomNumeric);
        return idGeneratorMap;
    }

}
