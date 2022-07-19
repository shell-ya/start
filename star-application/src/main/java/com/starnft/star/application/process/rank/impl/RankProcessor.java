package com.starnft.star.application.process.rank.impl;

import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.google.common.collect.Lists;
import com.starnft.star.application.process.rank.IRankProcessor;
import com.starnft.star.application.process.rank.req.RankReq;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.rank.core.rank.core.IRankService;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import com.starnft.star.domain.rank.core.rank.model.RankItemMetaData;
import com.starnft.star.domain.rank.core.rank.model.res.InvitationHistory;
import com.starnft.star.domain.rank.core.rank.model.res.InvitationHistoryItem;
import com.starnft.star.domain.rank.core.rank.model.res.Rankings;
import com.starnft.star.domain.rank.core.rank.model.res.RankingsItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Date 2022/7/10 2:53 PM
 * @Author ： shellya
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RankProcessor implements IRankProcessor {

    final IRankService rankService;

    @Override
    @Cached(name = StarConstants.RANK_CACHE_NAME,
            expire = 6,
            cacheType = CacheType.REMOTE,
            timeUnit = TimeUnit.MINUTES)
    @CacheRefresh(refresh = 5,timeUnit = TimeUnit.MINUTES,stopRefreshAfterLastAccess = 3)
    public Rankings rankings(RequestConditionPage<RankReq> rankPage) {
        //查询当前时间开启的活动
        RankDefinition nowRank = getNowRank();
        Long total = rankService.getRankSize(nowRank.getRankName());

        if (Objects.isNull(total) || total == 0) return null;

        int start = (rankPage.getPage() - 1) * rankPage.getSize();
        int end = rankPage.getPage() * rankPage.getSize() -1;
        //先取出分页数据
        List<RankingsItem> list = rankService.getRankDatasByPage(nowRank.getRankName(), start, end);

        Rankings result = new Rankings();
        result.setRankName(nowRank.getRankName());
        result.setRankItem(list);
        result.setTotal(total);
        result.setPage(rankPage.getPage());
        result.setPageSize(rankPage.getSize());
        return result;
    }

    @Override
    public  List<InvitationHistoryItem> invitationHistory(RequestConditionPage<RankReq> historyReq) {
        //查询个人邀请记录
        List<InvitationHistoryItem> rankInvitation = rankService.getRankInvitation(getNowRank().getRankName(), historyReq.getCondition().getUserId(), historyReq.getPage(), historyReq.getSize());

        return rankInvitation;
    }

    @Override
    public Long personNum(RankReq rankReq) {
        Long rankNum = rankService.getRankNum(getNowRank().getRankName(), rankReq.getUserId());
        if (Objects.isNull(rankNum))  return  0L;
        return  rankNum + 1;
    }

    public RankDefinition getNowRank(){
        List<RankDefinition> allRank = rankService.getAllRank();
        if (Objects.isNull(allRank)) throw new StarException(StarError.NOT_FOUND_ACTIVITY);

        RankDefinition rankDefinition = allRank.stream().filter(item -> item.getStartTime().before(new Date()))
                .filter(item -> item.getEndTime().after(new Date())).findAny().orElse(null);

        if (Objects.isNull(rankDefinition)) throw new StarException(StarError.NOT_FOUND_ACTIVITY);

        return rankDefinition;
    }


}
