package com.starnft.star.domain.article.service.impl;

import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveNumbersReq;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.req.UserHaveThemeReq;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.model.vo.UserSeriesVO;
import com.starnft.star.domain.article.model.vo.UserThemeVO;
import com.starnft.star.domain.article.repository.IUserThemeRepository;
import com.starnft.star.domain.article.service.UserThemeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserThemeServiceImpl implements UserThemeService {
    @Resource
    IUserThemeRepository userThemeRepository;

    @Override
    public ResponsePageResult<UserSeriesVO> queryUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq) {
        return this.userThemeRepository.queryUserArticleSeriesInfo(userHaveSeriesReq);
    }

    @Override
    public ResponsePageResult<UserThemeVO> queryUserArticleThemeInfo(UserHaveThemeReq userHaveThemeReq) {
        return this.userThemeRepository.queryUserArticleThemeInfo(userHaveThemeReq);
    }

    @Override
    public ResponsePageResult<UserNumbersVO> queryUserArticleNumberInfo(UserHaveNumbersReq userHaveNumbersReq) {
        return this.userThemeRepository.queryUserArticleNumberInfo(userHaveNumbersReq);
    }

    @Override
    public UserNumbersVO queryUserNumberInfo(Long uid, Long numberId,UserNumberStatusEnum statusEnum) {
        return this.userThemeRepository.queryUserNumberInfo(uid, numberId,statusEnum);
    }

    @Override
    public Boolean modifyUserNumberStatus(Long uid, Long numberId, UserNumberStatusEnum beforeStatusEnum, UserNumberStatusEnum statusEnum) {
        return this.userThemeRepository.modifyUserNumberStatus(uid, numberId, beforeStatusEnum,statusEnum);
    }
}
