package com.starnft.star.domain.rank.core.rank.core;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import com.starnft.star.domain.rank.core.rank.model.RankItemMetaData;
import com.starnft.star.domain.rank.core.rank.model.res.InvitationHistoryItem;
import com.starnft.star.domain.rank.core.rank.model.res.InvitationItem;
import com.starnft.star.domain.rank.core.rank.model.res.RankingsItem;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RankServiceImpl implements IRankService {
    @Autowired
    @Qualifier("RankRedisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public boolean createRank(String rankName, RankDefinition rankDefinition) {
        return redisTemplate.opsForHash().putIfAbsent(RedisKey.RANK_LIST.getKey(), rankName, JSONUtil.toJsonStr(rankDefinition));
    }

    @Override
    public void deleteRank(String rankName) {
        redisTemplate.opsForHash().delete(RedisKey.RANK_LIST.getKey(), rankName);
    }

    @Override
    public void deleteAllRank() {
        redisTemplate.delete(RedisKey.RANK_LIST.getKey());
    }

    @Override
    public boolean hasRank(String rankName) {
        Boolean hasRankList = redisTemplate.hasKey(RedisKey.RANK_LIST.getKey());
        if (!hasRankList) return false;
        return redisTemplate.opsForHash().hasKey(RedisKey.RANK_LIST.getKey(), rankName);
    }

    @Override
    public Double put(String rankName, String key, double value, RankItemMetaData rankItemMetaData) {
        boolean hasRank = hasRank(rankName);
        if (!hasRank) throw new RuntimeException("请先创建排行榜");
        //个人拉新总人数

        Boolean isSetSuccess = redisTemplate.opsForHash().putIfAbsent(String.format(RedisKey.RANK_TOTAL_USER.getKey(), rankName, key), rankItemMetaData.getChildrenId().toString(), JSONUtil.toJsonStr(rankItemMetaData));
        Double result=null;
        if (isSetSuccess) {
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
            result = redisTemplate.opsForZSet().incrementScore(String.format(RedisKey.RANK_ITEM.getKey(), rankName), key, value);
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
    public Long getRankNum(String rankName, Long id) {
        return redisTemplate.opsForZSet().rank(String.format(RedisKey.RANK_ITEM.getKey(), rankName), id.toString());
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
        if (Objects.isNull(result)) return null;
        return JSONUtil.toBean(result.toString(), RankDefinition.class);
    }

    @Override
    public RankItemMetaData getRankDataByRankNum(String rankName, int rankNum) {


        return null;
    }

    @Override
    public List<RankingsItem> getRankDatasByPage(String rankName, int page, int pageSize) {
            Set set = 1 == page ?
                    redisTemplate.opsForZSet().reverseRangeByScore(String.format(RedisKey.RANK_ITEM.getKey(), rankName), 0, pageSize - 1):
                    redisTemplate.opsForZSet().reverseRangeByScore(String.format(RedisKey.RANK_ITEM.getKey(), rankName), page, pageSize );
            if (Objects.isNull(set)) return null;

        List<RankingsItem> items = Lists.newArrayList();
        for (Object userId :
                set) {
            RankingsItem rankingsItem = new RankingsItem();
            rankingsItem.setAccount(Long.valueOf((String) userId));
            rankingsItem.setTotal(redisTemplate.opsForHash().size(String.format(RedisKey.RANK_TOTAL_USER.getKey(),rankName,userId)));
            rankingsItem.setValid(redisTemplate.opsForHash().size(String.format(RedisKey.RANK_VALID_USER.getKey(),rankName,userId)));
            items.add(rankingsItem);
        }

        return items;

    }

    @Override
    public List<RankItemMetaData> getRankDatasAroundId(String rankName, int id, int beforeNum, int afterNum) {
        return null;
    }

    @Override
    public List<InvitationHistoryItem> getRankInvitation(String rankName, Long userId,int page,int pageSize) {

        Cursor<Map.Entry<Object,Object>> total = redisTemplate.opsForHash().scan(String.format(RedisKey.RANK_TOTAL_USER.getKey(), rankName,userId), ScanOptions.NONE);
//        Cursor<Map.Entry<Object,Object>> valid = redisTemplate.opsForHash().scan(String.format(RedisKey.RANK_VALID_USER.getKey(), rankName,userId), ScanOptions.NONE);
        Set validKeys = redisTemplate.opsForHash().keys(String.format(RedisKey.RANK_VALID_USER.getKey(), rankName, userId));
        ArrayList<InvitationHistoryItem> items = Lists.newArrayList();
        while (total.hasNext()){
            Map.Entry<Object, Object> totalEntry = total.next();
//            Map.Entry<Object, Object> validEntry = valid.hasNext() ? valid.next() : null;
            InvitationHistoryItem item = JSONUtil.toBean(totalEntry.getValue().toString(), InvitationHistoryItem.class);
            item.setAccount(totalEntry.getKey().toString());
            if (validKeys.contains(totalEntry.getKey())){
                item.setValid(1);
            }
            items.add(item);
        }
        try{
            total.close();
//            valid.close();
        }catch (Exception e){
            log.error("游标关闭异常",e);
            throw new StarException(StarError.SYSTEM_ERROR);
        }
        return items;
    }
}
