package com.starnft.star.domain.article.repository;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.model.vo.UserSeriesVO;
import com.starnft.star.domain.article.model.vo.UserThemeVO;

public interface IUserThemeRepository {
    public ResponsePageResult<UserSeriesVO> queryUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq);
    public ResponsePageResult<UserThemeVO> queryUserArticleThemeInfo(UserHaveSeriesReq userHaveSeriesReq);
    public ResponsePageResult<UserNumbersVO> queryUserArticleNumberInfo(UserHaveSeriesReq userHaveSeriesReq);

}
