package com.starnft.star.domain.article.service;

import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveNumbersReq;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.req.UserHaveThemeReq;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.model.vo.UserSeriesVO;
import com.starnft.star.domain.article.model.vo.UserThemeVO;

public interface UserThemeService {
    ResponsePageResult<UserSeriesVO> queryUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq);

    ResponsePageResult<UserThemeVO> queryUserArticleThemeInfo(UserHaveThemeReq userHaveThemeReq);

    ResponsePageResult<UserNumbersVO> queryUserArticleNumberInfo(UserHaveNumbersReq userHaveNumbersReq);

    UserNumbersVO queryUserNumberInfo(Long uid, Long numberId);

    Boolean modifyUserNumberStatus(Long uid, Long numberId, UserNumberStatusEnum statusEnum);
}
