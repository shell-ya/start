package com.starnft.star.application.process.compose;

import com.starnft.star.application.process.compose.model.req.UserMaterialReq;
import com.starnft.star.application.process.compose.model.res.ComposeCategoryMaterialRes;
import com.starnft.star.application.process.compose.model.res.ComposeDetailRes;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.compose.model.req.ComposeManageReq;
import com.starnft.star.domain.compose.model.res.ComposeManageRes;

import java.util.List;
import java.util.Map;

public interface IComposeCore {
    List<ComposeCategoryMaterialRes> composeMaterial(Long id);

    ComposeDetailRes composeDetails(Long id);

    ResponsePageResult<UserNumbersVO> composeUserMaterial(Long userId, RequestConditionPage<UserMaterialReq> userMaterialReq);

    ComposeManageRes composeManage(ComposeManageReq composeManageReq);


}
