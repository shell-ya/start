package com.starnft.star.domain.event.model.service;

import com.starnft.star.domain.event.model.req.EventActivityExtReq;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;

import java.util.List;

public interface IEventActivityService {
   List<EventActivityExtRes> queryEventActivityParams(EventActivityExtReq eventActivityExtReq);
}
