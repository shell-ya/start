package com.starnft.star.domain.event.model.service;

import com.starnft.star.domain.event.model.req.EventActivityExtReq;
import com.starnft.star.domain.event.model.req.EventActivityReq;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import com.starnft.star.domain.event.model.res.EventActivityRes;

import java.util.List;

public interface IEventActivityService {
   List<EventActivityExtRes> queryEventActivityParams(EventActivityExtReq eventActivityExtReq);
   EventActivityRes queryEventActivity(EventActivityReq eventActivityReq);
}
