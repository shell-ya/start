package com.starnft.star.application.process.order.white.rule;

import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.domain.component.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("worldCreationWhiteRule")
public class WorldCreationWhiteRule extends AbstractWhiteRule {
    @Resource
    RedisUtil redisUtil;

    @Override
    public boolean verifyRule(Long uid, Long themeId) {
        Long times = 0L;
        synchronized (this) {
            redisUtil.hincr(RedisKey.SECKILL_GOODS_PRIORITY_TIMES.getKey(), String.valueOf(uid), 1L);
            times = redisUtil.hdecr(RedisKey.SECKILL_GOODS_PRIORITY_TIMES.getKey(), String.valueOf(uid), 1L);
        }

        if (times > 0L) {
            return true;
        }
        return false;
    }
}
