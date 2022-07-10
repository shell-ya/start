package com.starnft.star.application.process.rank;

import com.starnft.star.application.process.rank.req.RankReq;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.domain.rank.core.rank.model.res.InvitationHistory;
import com.starnft.star.domain.rank.core.rank.model.res.InvitationHistoryItem;
import com.starnft.star.domain.rank.core.rank.model.res.Rankings;

import java.util.List;

/**
 * @Date 2022/7/10 2:53 PM
 * @Author ： shellya
 */
public interface IRankProcessor {
    /**
     * 排行榜记录
     * @param rankName
     * @return
     */
    Rankings rankings(RequestConditionPage<RankReq> rankName);

    /**
     * 个人邀请记录
     * @param historyReq
     * @return
     */
    List<InvitationHistoryItem> invitationHistory(RequestConditionPage<RankReq> historyReq);

    Long personNum(RankReq rankReq);
}
