package com.starnft.star.domain.article.repository;

import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;

public interface IUserThemeRepository {
    public void  queryUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq);

}
