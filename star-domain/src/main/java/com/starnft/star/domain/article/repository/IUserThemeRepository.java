package com.starnft.star.domain.article.repository;

import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveNumbersReq;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.req.UserHaveThemeReq;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.model.vo.UserSeriesVO;
import com.starnft.star.domain.article.model.vo.UserThemeDetailVo;
import com.starnft.star.domain.article.model.vo.UserThemeVO;

import java.util.List;

public interface IUserThemeRepository {
    ResponsePageResult<UserSeriesVO> queryUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq);

    ResponsePageResult<UserThemeVO> queryUserArticleThemeInfo(UserHaveThemeReq userHaveThemeReq);

    ResponsePageResult<UserNumbersVO> queryUserArticleNumberInfo(UserHaveNumbersReq userHaveNumbersReq);

    List<UserNumbersVO> queryUserArticleNumberInfoByThemeIds(Long uid, List<Long> themeIds, UserNumberStatusEnum statusEnum);

    UserNumbersVO queryUserNumberInfo(Long uid, Long numberId,UserNumberStatusEnum statusEnum) ;

    Boolean modifyUserNumberStatus(Long uid, Long numberId,UserNumberStatusEnum beforeStatusEnum, UserNumberStatusEnum statusEnum);
}
