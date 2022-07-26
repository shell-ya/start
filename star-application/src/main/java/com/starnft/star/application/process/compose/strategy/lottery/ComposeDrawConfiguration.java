package com.starnft.star.application.process.compose.strategy.lottery;

import com.starnft.star.common.constant.RedisKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component("composeDrawConfiguration")
public class ComposeDrawConfiguration {
    @Resource
    RedisTemplate redisTemplate;
    public Integer getComposeDraw(){
        Integer composeDrawStrategy = (Integer) redisTemplate.opsForValue().get(RedisKey.COMPOSE_DRAW_STRATEGY);
        return Optional.ofNullable(composeDrawStrategy).orElse(1);
    }

}
