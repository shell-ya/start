package com.starnft.star.application.process.event.strategy;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import org.springframework.stereotype.Component;

@Component("rankScopeStrategy")
public class RankScopeStrategy implements EventStrategy
{


    @Override
    public StarConstants.EventType getEventType() {
        return StarConstants.EventType.Rank;
    }

    @Override
    public void handler(EventActivityExtRes ext, ActivityEventReq activityEventReq) {

    }
}
