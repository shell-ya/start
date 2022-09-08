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
import com.starnft.star.domain.component.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class UserThemeServiceImpl implements UserThemeService {
    @Resource
    IUserThemeRepository userThemeRepository;

    @Resource
    RedisUtil redisUtil;

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
    public UserNumbersVO queryUserNumberInfo(Long uid, Long numberId, UserNumberStatusEnum statusEnum) {
        return this.userThemeRepository.queryUserNumberInfo(uid, numberId, statusEnum);
    }

    @Override
    public Boolean modifyUserNumberStatus(Long uid, Long numberId, BigDecimal price, UserNumberStatusEnum beforeStatusEnum, UserNumberStatusEnum statusEnum) {
        return this.userThemeRepository.modifyUserNumberStatus(uid, numberId, price,beforeStatusEnum, statusEnum);
    }

    @Override
    public Boolean modifyUserBatchNumberStatus(Long uid, List<Long> numberId, UserNumberStatusEnum beforeStatusEnum, UserNumberStatusEnum statusEnum) {
        return this.userThemeRepository.modifyUserBatchNumberStatus(uid, numberId, beforeStatusEnum, statusEnum);
    }

    @Override
    public ResponsePageResult<UserNumbersVO> queryUserArticleNumberInfoByThemeIds(Long uid, List<Long> themeIds, UserNumberStatusEnum statusEnum,Integer page,Integer size) {
        return this.userThemeRepository.queryUserArticleNumberInfoByThemeIds(uid, themeIds, statusEnum,page,size);
    }

    @Override
    public List<UserNumbersVO> queryUserArticleNumberInfoByNumberIds(Long uid, List<Long> numberIds, UserNumberStatusEnum statusEnum) {
        return this.userThemeRepository.queryUserArticleNumberInfoByNumberIds(uid, numberIds, statusEnum);
    }

    @Override
    public ResponsePageResult<UserNumbersVO> queryUserArticleNumberInfoBySeriesIds(Long userId, List<Long> seriesIds, UserNumberStatusEnum purchased, int page, int size) {
        return this.userThemeRepository.queryUserArticleNumberInfoBySeriesIds(userId, seriesIds, purchased,page,size);
    }
}
