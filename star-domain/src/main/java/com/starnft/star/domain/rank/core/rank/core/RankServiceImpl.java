package com.starnft.star.domain.rank.core.rank.core;

import cn.hutool.json.JSONUtil;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.domain.rank.core.rank.model.RankData;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
@Component
public class RankServiceImpl implements IRankService {
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public boolean createRank(String rankName, RankDefinition rankDefinition) {
        return redisTemplate.opsForHash().putIfAbsent(RedisKey.RANK_LIST.getKey(),rankName, JSONUtil.toJsonStr(rankDefinition));
    }
    @Override
    public void deleteRank(String rankName) {
       redisTemplate.opsForHash().delete(RedisKey.RANK_LIST.getKey(),rankName);
    }
    @Override
    public void deleteAllRank(){
        redisTemplate.delete(RedisKey.RANK_LIST.getKey());
    }
    @Override
    public boolean hasRank(String rankName) {
        Boolean hasRankList = redisTemplate.hasKey(RedisKey.RANK_LIST.getKey());
        if (!hasRankList) return false;
        return  redisTemplate.opsForHash().hasKey(RedisKey.RANK_LIST.getKey(),rankName);
    }
    @Override
    public Boolean put(String rankName,String  key,double value,RankData rankData) {
        boolean hasRank = hasRank(rankName);
        if (!hasRank)  throw new RuntimeException("请先创建排行榜");
        //todo 依据rankDefinition 进行不同的配置信息
         RankDefinition rankDefinition = JSONUtil.toBean(redisTemplate.opsForHash().get(RedisKey.RANK_LIST.getKey(), rankName).toString(), RankDefinition.class);
         redisTemplate.opsForHash().putIfAbsent(String.format(RedisKey.RANK_STORE.getKey()),key,JSONUtil.toJsonStr(rankData));
        return  redisTemplate.opsForZSet().add(String.format(RedisKey.RANK_STORE.getKey(), rankName), key, value);
    }

    @Override
    public long[] put(String rankName, int id, long... value) {
        return new long[0];
    }

    @Override
    public long putIfAbsent(String rankName, int id, long value) {
        return 0;
    }

    @Override
    public long[] putIfAbsent(String rankName, int id, long... value) {
        return new long[0];
    }

    @Override
    public long putByField(String rankName, int id, int field, long value) {
        return 0;
    }

    @Override
    public long[] delete(String rankName, int id) {
        return new long[0];
    }

    @Override
    public boolean has(String rankName, int id) {
        return false;
    }

    @Override
    public int getRankNum(String rankName, int id) {
        return 0;
    }

    @Override
    public RankData getRankDataById(String rankName, int id) {
        return null;
    }

    @Override
    public int getRankId(String rankName, int rankNum) {
        return 0;
    }

    @Override
    public RankData getRankDataByRankNum(String rankName, int rankNum) {
        return null;
    }

    @Override
    public List<RankData> getRankDatasByPage(String rankName, int page, int pageSize) {
        return null;
    }

    @Override
    public List<RankData> getRankDatasAroundId(String rankName, int id, int beforeNum, int afterNum) {
        return null;
    }
}
