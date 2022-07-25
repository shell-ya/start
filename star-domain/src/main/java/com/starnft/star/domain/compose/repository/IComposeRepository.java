package com.starnft.star.domain.compose.repository;

import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.compose.model.req.ComposeReq;
import com.starnft.star.domain.compose.model.res.ComposeRes;

public interface IComposeRepository {
    ResponsePageResult<ComposeRes> queryComposePageByCondition(RequestConditionPage<ComposeReq> conditionPage);
}
