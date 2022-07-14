package com.starnft.star.application.process.rank.impl;

import com.google.common.collect.Lists;
import com.starnft.star.application.process.rank.IRankProcessor;
import com.starnft.star.application.process.rank.req.RankReq;
import com.starnft.star.common.constant.RedisKey;
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
    public ResponsePageResult<RankingsItem> rankings(RequestConditionPage<RankReq> rankPage) {
        //查询当前时间开启的活动
        RankDefinition nowRank = getNowRank();
        Long total = rankService.getRankSize(nowRank.getRankName());

        if (Objects.isNull(total) || total == 0) return null;

        int start = (rankPage.getPage() - 1) * rankPage.getSize();
        int end = rankPage.getPage() * rankPage.getSize() -1;
        //先取出分页数据
        List<RankingsItem> list = rankService.getRankDatasByPage(nowRank.getRankName(), start, end);

        ResponsePageResult<RankingsItem> result = new ResponsePageResult<>();
        result.setList(list);
        result.setTotal(total);
        result.setPage(rankPage.getPage());
        result.setSize(rankPage.getSize());
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
        return rankService.getRankNum(getNowRank().getRankName(),rankReq.getUserId());
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
