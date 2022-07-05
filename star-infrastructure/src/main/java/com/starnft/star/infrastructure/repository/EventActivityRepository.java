package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starnft.star.domain.event.model.req.EventActivityExtReq;
import com.starnft.star.domain.event.model.req.EventActivityReq;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import com.starnft.star.domain.event.model.res.EventActivityRes;
import com.starnft.star.domain.event.repository.IEventActivityRepository;
import com.starnft.star.infrastructure.entity.activityEvent.StarNftActivity;
import com.starnft.star.infrastructure.entity.activityEvent.StarNftActivityExt;
import com.starnft.star.infrastructure.mapper.activityEvent.StarNftActivityExtMapper;
import com.starnft.star.infrastructure.mapper.activityEvent.StarNftActivityMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventActivityRepository implements IEventActivityRepository {
    @Resource
    StarNftActivityExtMapper starNftActivityExtMapper;
    @Resource
    StarNftActivityMapper starNftActivityMapper;
    @Override
    public List<EventActivityExtRes> queryEventActivityExtArrayBySignOrEvent(EventActivityExtReq eventActivityExtReq) {
        QueryWrapper<StarNftActivityExt> wrapper = new QueryWrapper<StarNftActivityExt>();
        if (StringUtils.isNotBlank(eventActivityExtReq.getActivitySign())){
             wrapper.eq(StarNftActivityExt.COL_ACTIVITY_SIGN,eventActivityExtReq.getActivitySign());
        }
        if (StringUtils.isNotBlank(eventActivityExtReq.getEventSign())){
             wrapper.eq(StarNftActivityExt.COL_EVENT_SIGN, eventActivityExtReq.getEventSign());
        }
        List<StarNftActivityExt> starNftActivityArrays = starNftActivityExtMapper.selectList(wrapper);
        return starNftActivityArrays.stream().map(item -> {
            EventActivityExtRes eventActivityExtRes = new EventActivityExtRes();
            eventActivityExtRes.setExtType(item.getExtType());
            eventActivityExtRes.setEventSign(item.getEventSign());
            eventActivityExtRes.setParams(item.getParams());
            return eventActivityExtRes;
        }).collect(Collectors.toList());
    }

    @Override
    public EventActivityRes queryEventActivity(EventActivityReq eventActivityExtReq) {
        QueryWrapper<StarNftActivity> wrapper = new QueryWrapper<StarNftActivity>();
        if (StringUtils.isNotBlank(eventActivityExtReq.getActivitySign())){
            wrapper.eq(StarNftActivity.COL_ACTIVITY_SIGN,eventActivityExtReq.getActivitySign());
        }
        StarNftActivity starNftActivities = starNftActivityMapper.selectOne(wrapper);

            EventActivityRes eventActivityRes = new EventActivityRes();
            eventActivityRes.setActivityName(starNftActivities.getActivityName());
            eventActivityRes.setActivitySign(starNftActivities.getActivitySign());
            eventActivityRes.setActivityStatus(starNftActivities.getActivityStatus());
            eventActivityRes.setExtend(starNftActivities.getExtend());
            eventActivityRes.setEndTime(starNftActivities.getEndTime());
            eventActivityRes.setIsTimes(starNftActivities.getIsTimes());
            eventActivityRes.setId(starNftActivities.getId());
            return  eventActivityRes;

    }


}
