package com.starnft.star.application.process.article;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveNumbersReq;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.req.UserHaveThemeReq;
import com.starnft.star.domain.article.model.req.UserThemeDetailReq;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.model.vo.UserSeriesVO;
import com.starnft.star.domain.article.model.vo.UserThemeDetailVo;
import com.starnft.star.domain.article.model.vo.UserThemeVO;

/**
 * @author Harlan
 * @date 2022/07/06 13:38
 */
public interface IUserArticleCore {
    ResponsePageResult<UserSeriesVO> obtainUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq);

    ResponsePageResult<UserThemeVO> obtainUserArticleThemeInfo(UserHaveThemeReq userHaveThemeReq);

    ResponsePageResult<UserNumbersVO> obtainUserArticleNumberInfo(UserHaveNumbersReq userHaveNumbersReq);

    UserThemeDetailVo obtainThemeDetail(UserThemeDetailReq req);
}
