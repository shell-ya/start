package com.starnft.star.domain.compose.repository;

import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.compose.model.req.ComposeReq;
import com.starnft.star.domain.compose.model.res.ComposeRes;

import java.util.List;

public interface IComposeRepository {
    List<ComposeRes> queryComposePageByCondition(ComposeReq composeReq);

    ComposeRes queryComposeById(Long id);
}
