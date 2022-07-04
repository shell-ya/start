package com.starnft.star.application.process.event.strategy;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("scopeStrategy")
public  class ScopeStrategy  implements  EventStrategy{
     @Resource
     private IScopeCore iScopeCore;
     public void handler(EventActivityExtRes ext, ActivityEventReq activityEventReq){

//         iScopeCore.userScopeManageAdd(addScoreDTO);
     }

    @Override
    public StarConstants.EventType getEventType() {
        return StarConstants.EventType.Scope;
    }
}
