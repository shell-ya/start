package com.starnft.star.domain.scope.repository;

import com.starnft.star.domain.scope.model.res.ScopeConfigRes;

import java.util.List;

public interface IScopeConfigRepository {
    List<ScopeConfigRes> queryScopeConfigByCode(Integer code);
}
