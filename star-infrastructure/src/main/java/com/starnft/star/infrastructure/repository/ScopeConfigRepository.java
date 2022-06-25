package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.scope.model.res.ScopeConfigRes;
import com.starnft.star.domain.scope.repository.IScopeConfigRepository;
import com.starnft.star.infrastructure.entity.scope.StarNftScopeEventConfig;
import com.starnft.star.infrastructure.mapper.scope.StarNftScopeEventConfigMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ScopeConfigRepository implements IScopeConfigRepository {
    @Resource
    StarNftScopeEventConfigMapper starNftScopeEventConfigMapper;

    @Override
    public List<ScopeConfigRes> queryScopeConfigByCode(Integer code) {
        List<StarNftScopeEventConfig> starNftScopeEventConfig = starNftScopeEventConfigMapper
                .selectList(new QueryWrapper<StarNftScopeEventConfig>()
                        .eq(StarNftScopeEventConfig.COL_EVENT_CODE, code)
                        .eq(StarNftScopeEventConfig.COL_IS_DELETED, Boolean.FALSE)
                        .eq(StarNftScopeEventConfig.COL_EVENT_STATUS, StarConstants.Event.EVENT_STATUS_OPEN)
                );
        return starNftScopeEventConfig.stream().map(item -> {
            ScopeConfigRes scopeConfigRes = new ScopeConfigRes();
            scopeConfigRes.setEventCode(item.getEventCode());
            scopeConfigRes.setEventDesc(item.getEventDesc());
            scopeConfigRes.setScopeType(item.getScopeType());
            scopeConfigRes.setEventStatus(item.getEventStatus());
            scopeConfigRes.setEventName(item.getEventName());
            return scopeConfigRes;
        }).collect(Collectors.toList());
    }
}
