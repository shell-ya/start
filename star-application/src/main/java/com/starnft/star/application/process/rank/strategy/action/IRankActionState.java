package com.starnft.star.application.process.rank.strategy.action;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;

public interface IRankActionState {
    public StarConstants.EventSign getState();
    public void manage(ActivityEventReq activityEventReq, RankDefinition rankDefinition);
}
