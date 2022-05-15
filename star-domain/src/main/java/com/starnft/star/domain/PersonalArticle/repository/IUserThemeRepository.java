package com.starnft.star.domain.PersonalArticle.repository;

import com.starnft.star.domain.PersonalArticle.model.req.UserHaveSeriesReq;

public interface IUserThemeRepository {
    public void  queryUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq);

}
