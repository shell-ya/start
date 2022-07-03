package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.scope.model.res.ScopeConfigRes;
import com.starnft.star.domain.scope.repository.IScopeConfigRepository;
import com.starnft.star.infrastructure.entity.scope.StarNftScopeEventConfig;
import com.starnft.star.infrastructure.mapper.scope.StarNftScopeEventConfigMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ScopeConfigRepository implements IScopeConfigRepository {
    @Resource
    StarNftScopeEventConfigMapper starNftScopeEventConfigMapper;

    @Override
    public ScopeConfigRes queryScopeConfigByScopeType(Integer scopeType) {
         StarNftScopeEventConfig starNftScopeEventConfig = starNftScopeEventConfigMapper
                .selectOne(new QueryWrapper<StarNftScopeEventConfig>()
                        .eq(StarNftScopeEventConfig.COL_SCOPE_TYPE, scopeType)
                        .eq(StarNftScopeEventConfig.COL_IS_DELETED, Boolean.FALSE)
                        .eq(StarNftScopeEventConfig.COL_EVENT_STATUS, StarConstants.Event.EVENT_STATUS_OPEN)
                );

        ScopeConfigRes scopeConfigRes = new ScopeConfigRes();
        scopeConfigRes.setEventCode(starNftScopeEventConfig.getEventCode());
        scopeConfigRes.setEventDesc(starNftScopeEventConfig.getEventDesc());
        scopeConfigRes.setScopeType(starNftScopeEventConfig.getScopeType());
        scopeConfigRes.setEventStatus(starNftScopeEventConfig.getEventStatus());
        scopeConfigRes.setEventName(starNftScopeEventConfig.getEventName());
        return scopeConfigRes;

    }
}
