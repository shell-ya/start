package com.starnft.star.domain.scope.repository;

import com.starnft.star.domain.scope.model.res.ScopeConfigRes;

public interface IScopeConfigRepository {
    ScopeConfigRes queryScopeConfigByScopeType(Integer scopeType);
}
