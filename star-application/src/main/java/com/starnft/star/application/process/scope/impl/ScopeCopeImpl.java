package com.starnft.star.application.process.scope.impl;

import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.UserScopeMessageVO;
import com.starnft.star.domain.scope.model.res.ScopeConfigRes;
import com.starnft.star.domain.scope.service.IScopeConfigService;
import com.starnft.star.domain.scope.service.IUserScopeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ScopeCopeImpl implements IScopeCore {
    @Resource
    IUserScopeService iUserScopeService;
    @Resource
    IScopeConfigService iScopeConfigService;
    @Override
    public void calculateUserScope(UserScopeMessageVO userScopeMessageVO) {
        //获取是事件
        Integer eventGroup = userScopeMessageVO.getEventGroup();
        //查询不到积分配置直接返回不进行积分操作
        List<ScopeConfigRes> scopeConfigRes1 = iScopeConfigService.queryScoreConfigByCode(eventGroup);
//        if (Objects.isNull(scopeConfigRes)) return;


    }
}
