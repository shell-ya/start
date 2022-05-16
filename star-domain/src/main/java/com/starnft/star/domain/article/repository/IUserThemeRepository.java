package com.starnft.star.domain.article.repository;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.vo.UserThemeVO;

public interface IUserThemeRepository {
    public ResponsePageResult<UserThemeVO> queryUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq);

}
