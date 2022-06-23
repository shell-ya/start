package com.starnft.star.domain.scope.service.impl;

import com.starnft.star.domain.scope.model.res.ScopeConfigRes;
import com.starnft.star.domain.scope.repository.IScopeConfigRepository;
import com.starnft.star.domain.scope.service.IScopeConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ScopeConfigServiceImpl implements IScopeConfigService {
    @Resource
    IScopeConfigRepository scopeConfigRepository;
    @Override
    public ScopeConfigRes queryScoreConfigByCode(Integer code) {
        return scopeConfigRepository.queryScopeConfigByCode(code);
    }
}
