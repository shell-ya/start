package com.starnft.star.domain.rank.core.rank.core;

import cn.hutool.json.JSONUtil;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import com.starnft.star.domain.rank.core.rank.model.RankItemMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class RankServiceImpl implements IRankService {
    @Autowired
    @Qualifier("RankRedisTemplate")
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
    public Double put(String rankName, String key, double value, RankItemMetaData rankItemMetaData) {
        boolean hasRank = hasRank(rankName);
        if (!hasRank)  throw new RuntimeException("请先创建排行榜");
        //个人拉新总人数
        if (Objects.nonNull(rankItemMetaData)){

            //uid=3 的拉新总人数
            redisTemplate.opsForHash().putIfAbsent(String.format(RedisKey.RANK_TOTAL_USER.getKey(),rankName,key),rankItemMetaData.getChildrenId(),JSONUtil.toJsonStr(rankItemMetaData));
        }
         //  redisTemplate.opsForHash().putIfAbsent(String.format(RedisKey.RANK_VALID_USER.getKey(),rankName,key),rankItemMetaData.getChildrenId(),JSONUtil.toJsonStr(rankItemMetaData));
//
        //总榜单
        return   redisTemplate.opsForZSet().incrementScore(String.format(RedisKey.RANK_ITEM.getKey(), rankName), key, value);
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
    public RankItemMetaData getRankDataById(String rankName, int id) {
        return null;
    }

    @Override
    public RankDefinition getRank(String rankName) {
        boolean hasRank = hasRank(rankName);
        if (!hasRank) return null;
        Object result = redisTemplate.opsForHash().get(RedisKey.RANK_LIST.getKey(), rankName);
        if (Objects.isNull(result)) return  null;
        return  JSONUtil.toBean(result.toString(),RankDefinition.class);
    }

    @Override
    public RankItemMetaData getRankDataByRankNum(String rankName, int rankNum) {
        return null;
    }

    @Override
    public List<RankItemMetaData> getRankDatasByPage(String rankName, int page, int pageSize) {
        return null;
    }

    @Override
    public List<RankItemMetaData> getRankDatasAroundId(String rankName, int id, int beforeNum, int afterNum) {
        return null;
    }
}
