package com.starnft.star.domain.article.service;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.vo.UserSeriesVO;

public interface UserThemeService {
    ResponsePageResult<UserSeriesVO> queryUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq);
}
