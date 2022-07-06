package com.starnft.star.domain.event.model.service;

import com.starnft.star.domain.event.model.req.EventActivityExtReq;
import com.starnft.star.domain.event.model.req.EventActivityReq;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import com.starnft.star.domain.event.model.res.EventActivityRes;

import java.util.List;
import java.util.Set;

public interface IEventActivityService {
   List<EventActivityExtRes> queryEventActivityParams(EventActivityExtReq eventActivityExtReq);
   EventActivityRes queryEventActivity(EventActivityReq eventActivityReq);

    List<EventActivityRes> queryEventActivityByIds(Set<Long> activityIds);
}
