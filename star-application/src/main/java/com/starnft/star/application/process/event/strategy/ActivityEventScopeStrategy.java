package com.starnft.star.application.process.event.strategy;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.AddScoreDTO;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;

@Component("activityEventScopeStrategy")
@Slf4j
public  class ActivityEventScopeStrategy  implements  ActivityEventStrategy{
     @Resource
     private IScopeCore iScopeCore;
     @Transactional
     public void handler(EventActivityExtRes ext, ActivityEventReq activityEventReq){
         String params = ext.getParams();
        if (StringUtils.isBlank(params)){
            return;
        }
         JSONObject configs = JSONUtil.parseObj(params);
         String template = configs.getStr("template");
         Integer scopeType = configs.getInt("scopeType");
         BigDecimal scale = Optional.ofNullable(configs.getBigDecimal("scale")).orElse(BigDecimal.ONE);
         Object number = activityEventReq.getParams().get("number");
         BigDecimal totalScope=new BigDecimal(number.toString()).multiply(scale);
         AddScoreDTO addScoreDTO = new AddScoreDTO();
         addScoreDTO.setTemplate(template);
         addScoreDTO.setScopeType(scopeType);
         addScoreDTO.setUserId(activityEventReq.getUserId());
         addScoreDTO.setScope(totalScope);
         iScopeCore.userScopeManageAdd(addScoreDTO);
     }

    @Override
    public StarConstants.ActivityType getEventType() {
        return StarConstants.ActivityType.Scope;
    }
}
