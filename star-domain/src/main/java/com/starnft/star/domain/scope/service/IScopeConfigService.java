package com.starnft.star.domain.scope.service;

import com.starnft.star.domain.scope.model.res.ScopeConfigRes;

import java.util.List;

public interface IScopeConfigService {

 List<ScopeConfigRes> queryScoreConfigByCode(Integer code);
}
