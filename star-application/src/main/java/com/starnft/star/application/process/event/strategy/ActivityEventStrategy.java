package com.starnft.star.application.process.event.strategy;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;

public interface ActivityEventStrategy {
    public StarConstants.ActivityType getEventType();
    public void handler(EventActivityExtRes ext, ActivityEventReq activityEventReq);
}
