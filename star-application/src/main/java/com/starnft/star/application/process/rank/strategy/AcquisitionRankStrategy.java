package com.starnft.star.application.process.rank.strategy;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import org.springframework.stereotype.Component;

@Component
public class AcquisitionRankStrategy implements IRankStrategy {
    @Override
    public StarConstants.RankTypes getRankType() {
        return StarConstants.RankTypes.Acquisition;
    }
    @Override
    public void handler(RankDefinition rankDefinition, EventActivityExtRes ext, ActivityEventReq activityEventReq) {

    }
}
