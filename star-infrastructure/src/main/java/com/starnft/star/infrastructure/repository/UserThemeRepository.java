package com.starnft.star.infrastructure.repository;

import com.github.pagehelper.PageHelper;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.repository.IUserThemeRepository;
import com.starnft.star.infrastructure.mapper.user.StarNftUserThemeMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserThemeRepository implements IUserThemeRepository {
    @Resource
    StarNftUserThemeMapper starNftUserThemeMapper;
    @Override
    public void queryUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq) {
        PageHelper.startPage(userHaveSeriesReq.getPage(), userHaveSeriesReq.getSize())
                .doSelectPageInfo(()-> starNftUserThemeMapper
                        .selectUserThemeToSeriesByUserId(userHaveSeriesReq));

    }
}
