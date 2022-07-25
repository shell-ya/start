package com.starnft.star.domain.compose.service;

import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.domain.compose.model.req.ComposeReq;

public interface IComposeService {
    Object composeList(RequestConditionPage<ComposeReq> reqRequestConditionPage);

}
