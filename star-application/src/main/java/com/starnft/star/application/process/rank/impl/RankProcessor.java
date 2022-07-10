package com.starnft.star.application.process.rank.impl;

import com.starnft.star.application.process.rank.IRankProcessor;
import com.starnft.star.application.process.rank.req.RankReq;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.domain.rank.core.rank.core.IRankService;
import com.starnft.star.domain.rank.core.rank.model.RankItemMetaData;
import com.starnft.star.domain.rank.core.rank.model.res.InvitationHistory;
import com.starnft.star.domain.rank.core.rank.model.res.InvitationHistoryItem;
import com.starnft.star.domain.rank.core.rank.model.res.Rankings;
import com.starnft.star.domain.rank.core.rank.model.res.RankingsItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Rankings rankings(RequestConditionPage<RankReq> rankName) {
        //先取出分页数据

        List<RankingsItem> RankingsItem = rankService.getRankDatasByPage(rankName.getCondition().getRankName(), rankName.getPage(), rankName.getSize());
        //用ID去查询总邀请人数及有效人数
        Rankings rankings = new Rankings();
        rankings.setRankName(rankName.getCondition().getRankName());
        rankings.setRankItem(RankingsItem);
        return rankings;
    }

    @Override
    public  List<InvitationHistoryItem> invitationHistory(RequestConditionPage<RankReq> historyReq) {
        //查询个人邀请记录
        List<InvitationHistoryItem> rankInvitation = rankService.getRankInvitation(historyReq.getCondition().getRankName(), historyReq.getCondition().getUserId(), historyReq.getPage(), historyReq.getSize());

        return rankInvitation;
    }

    @Override
    public Long personNum(RankReq rankReq) {
        return rankService.getRankNum(rankReq.getRankName(),rankReq.getUserId());
    }
}
