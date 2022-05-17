package com.starnft.star.domain.article.service.impl;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.vo.UserSeriesVO;
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
        return userThemeRepository.queryUserArticleSeriesInfo(userHaveSeriesReq);
    }
}
