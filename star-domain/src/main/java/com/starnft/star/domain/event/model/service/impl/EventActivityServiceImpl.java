package com.starnft.star.domain.event.model.service.impl;

import com.starnft.star.domain.event.model.req.EventActivityExtReq;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import com.starnft.star.domain.event.model.service.IEventActivityService;
import com.starnft.star.domain.event.repository.IEventActivityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EventActivityServiceImpl implements IEventActivityService {
    @Resource
    IEventActivityRepository iEventActivityRepository;
    @Override
    public List<EventActivityExtRes> queryEventActivityParams(EventActivityExtReq eventActivityExtReq) {
        return iEventActivityRepository.queryEventActivityExtArrayBySignOrEvent(eventActivityExtReq);
    }
}