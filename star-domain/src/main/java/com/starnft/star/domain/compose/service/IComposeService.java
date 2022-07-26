package com.starnft.star.domain.compose.service;

import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.compose.model.req.ComposeReq;
import com.starnft.star.domain.compose.model.res.ComposeCategoryRes;
import com.starnft.star.domain.compose.model.res.ComposePrizeRes;
import com.starnft.star.domain.compose.model.res.ComposeRes;

import java.util.List;

public interface IComposeService {
    ResponsePageResult<ComposeRes> composeList(RequestConditionPage<ComposeReq> reqRequestConditionPage);

    ComposeRes composeDetails(Long id);
    public List<ComposeCategoryRes> composeCategory(Long id);
    public ComposeCategoryRes composeCategoryByCategoryId(Long id);
    public List<ComposePrizeRes> composePrizeByComposeId(Long id);
}
