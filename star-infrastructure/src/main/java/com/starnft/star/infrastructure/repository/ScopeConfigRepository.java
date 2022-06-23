package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starnft.star.domain.scope.model.res.ScopeConfigRes;
import com.starnft.star.domain.scope.repository.IScopeConfigRepository;
import com.starnft.star.infrastructure.entity.scope.StarNftScopeEventConfig;
import com.starnft.star.infrastructure.mapper.scope.StarNftScopeEventConfigMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ScopeConfigRepository  implements IScopeConfigRepository {
    @Resource
    StarNftScopeEventConfigMapper starNftScopeEventConfigMapper;
    @Override
    public ScopeConfigRes queryScopeConfigByCode(Integer code) {
        StarNftScopeEventConfig starNftScopeEventConfig = starNftScopeEventConfigMapper
                .selectOne(new QueryWrapper<StarNftScopeEventConfig>()
                        .eq(StarNftScopeEventConfig.COL_EVENT_CODE, code)
                        .eq(StarNftScopeEventConfig.COL_IS_DELETED,Boolean.FALSE)
                );
        ScopeConfigRes scopeConfigRes = new ScopeConfigRes();
        scopeConfigRes.setEventCode(starNftScopeEventConfig.getEventCode());
        scopeConfigRes.setEventDesc(starNftScopeEventConfig.getEventDesc());
        scopeConfigRes.setEventStatus(starNftScopeEventConfig.getEventStatus());
        scopeConfigRes.setEventName(starNftScopeEventConfig.getEventName());
        return scopeConfigRes;
    }
}
