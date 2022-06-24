package com.starnft.star.application.process.scope.impl;

import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.UserScopeMessageVO;
import com.starnft.star.domain.scope.model.req.AddScoreRecordReq;
import com.starnft.star.domain.scope.model.req.UpdateUserScopeReq;
import com.starnft.star.domain.scope.model.res.ScopeConfigRes;
import com.starnft.star.domain.scope.service.IScopeConfigService;
import com.starnft.star.domain.scope.service.IScopeRecordService;
import com.starnft.star.domain.scope.service.IUserScopeService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class ScopeCopeImpl implements IScopeCore {
    @Resource
    IUserScopeService iUserScopeService;
    @Resource
    IScopeConfigService iScopeConfigService;
    @Resource
    IScopeRecordService iScopeRecordService;

    @Override
    @Transactional
    public void calculateUserScope(UserScopeMessageVO userScopeMessageVO) {
        //获取是事件
        Integer eventGroup = userScopeMessageVO.getEventGroup();
        //查询不到积分配置直接返回不进行积分操作
        List<ScopeConfigRes> scopeConfigRes = iScopeConfigService.queryScoreConfigByCode(eventGroup);
        for (ScopeConfigRes scopeConfig : scopeConfigRes) {
            UpdateUserScopeReq updateUserScopeReq = new UpdateUserScopeReq();
            updateUserScopeReq.setUserId(userScopeMessageVO.getUserId());
            updateUserScopeReq.setScopeType(scopeConfig.getScopeType());
            updateUserScopeReq.setScope(userScopeMessageVO.getScope());
            iUserScopeService.updateUserScopeByUserId(updateUserScopeReq);
            AddScoreRecordReq addScoreRecordReq = new AddScoreRecordReq();
            addScoreRecordReq.setScope(userScopeMessageVO.getScope());
            addScoreRecordReq.setUserId(userScopeMessageVO.getUserId());
            addScoreRecordReq.setRemarks(String.format(scopeConfig.getEventDesc(), userScopeMessageVO.getScope().toString()));
            addScoreRecordReq.setCreatedAt(new Date());
            addScoreRecordReq.setScopeType(scopeConfig.getScopeType());
            addScoreRecordReq.setMold(userScopeMessageVO.getScope().compareTo(BigDecimal.ZERO)<=0?0:1);
            iScopeRecordService.insertScopeRecord(addScoreRecordReq);
        }

    }
}
