package com.starnft.star.business.support.rank.core;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.starnft.star.business.support.rank.model.RankDefinition;
import com.starnft.star.business.support.rank.model.RankItemMetaData;
import com.starnft.star.business.support.rank.model.RankingsItem;
import com.starnft.star.common.constant.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    public void updateRank(String rankName, RankDefinition rankDefinition) {
         redisTemplate.opsForHash().put(RedisKey.RANK_LIST.getKey(),rankName, JSONUtil.toJsonStr(rankDefinition));
    }
    @Override
    public void deleteRank(String rankName) {
        redisTemplate.opsForHash().delete(RedisKey.RANK_LIST.getKey(), Arrays.asList(rankName).toArray());
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
    public Double put(String rankName, String  key, double value, RankItemMetaData rankItemMetaData) {
        boolean hasRank = hasRank(rankName);
        if (!hasRank) throw new RuntimeException("请先创建排行榜");
        //个人拉新总人数

        Boolean isSetSuccess = redisTemplate.opsForHash().putIfAbsent(String.format(RedisKey.RANK_TOTAL_USER.getKey(), rankName, key), rankItemMetaData.getChildrenId().toString(), JSONUtil.toJsonStr(rankItemMetaData));
        Double result=null;
        if (isSetSuccess) {
            Long rank = redisTemplate.opsForZSet().rank(String.format(RedisKey.RANK_ITEM_VALID.getKey(), rankName), key);
            if (Objects.isNull(rank)){
                redisTemplate.opsForZSet().incrementScore(String.format(RedisKey.RANK_ITEM_VALID.getKey(), rankName), key, value);
            }
            result = redisTemplate.opsForZSet().incrementScore(String.format(RedisKey.RANK_ITEM.getKey(), rankName), key, value);

        }


        //总榜单
        return result;
    }

    @Override
    public Double validPut(String rankName, String key, double value, RankItemMetaData rankItemMetaData) {
        boolean hasRank = hasRank(rankName);
        if (!hasRank) throw new RuntimeException("请先创建排行榜");
        //个人有效拉新

        Boolean isSetSuccess = redisTemplate.opsForHash().putIfAbsent(String.format(RedisKey.RANK_VALID_USER.getKey(), rankName, key), rankItemMetaData.getChildrenId().toString(), JSONUtil.toJsonStr(rankItemMetaData));
        Double result=null;
        if (isSetSuccess) {
            result = redisTemplate.opsForZSet().incrementScore(String.format(RedisKey.RANK_ITEM_VALID.getKey(), rankName), key, value);
        }


        //总榜单
        return result;
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
    public Long getRankNum(String rankName, Integer id) {
        return redisTemplate.opsForZSet().reverseRank(String.format(RedisKey.RANK_ITEM_VALID.getKey(), rankName), id.toString());
    }

    @Override
    public RankItemMetaData getRankDataById(String rankName, int id) {
        return null;
    }

    @Override
    public int getRankId(String rankName, int rankNum) {
        return 0;
    }

    @Override
    public RankItemMetaData getRankDataByRankNum(String rankName, int rankNum) {
        return null;
    }

    @Override
    public List<RankingsItem> getRankDatasByPage(String rankName, int page, int pageSize) {

        Set<ZSetOperations.TypedTuple<String>> reverseRangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(String.format(RedisKey.RANK_ITEM_VALID.getKey(),rankName), page, pageSize);
//        Set set = redisTemplate.opsForZSet().reverseRange(String.format(RedisKey.RANK_ITEM_VALID.getKey(), rankName), page, pageSize );
        if (Objects.isNull(reverseRangeWithScores)) return null;

        List<RankingsItem> items = Lists.newArrayList();
        for (ZSetOperations.TypedTuple<String> userId :
                reverseRangeWithScores) {
            RankingsItem rankingsItem = new RankingsItem();
//            rankingsItem.setAccount(Long.valueOf((String) userId));
            rankingsItem.setTotal(redisTemplate.opsForHash().size(String.format(RedisKey.RANK_TOTAL_USER.getKey(),rankName,userId.getValue())));
            rankingsItem.setValid(redisTemplate.opsForHash().size(String.format(RedisKey.RANK_VALID_USER.getKey(),rankName,userId.getValue())));
            rankingsItem.setPhone((String) redisTemplate.opsForHash().get(String.format(RedisKey.RANK_USER_MAPPING.getKey(), rankName), userId.getValue()));
            rankingsItem.setAccount(Long.parseLong(userId.getValue()));
            rankingsItem.setScore(userId.getScore());
            items.add(rankingsItem);
        }

        return items;
    }

    @Override
    public List<RankItemMetaData> getRankDatasAroundId(String rankName, int id, int beforeNum, int afterNum) {
        return null;
    }
}
