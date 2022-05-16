package com.starnft.star.infrastructure.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.vo.UserThemeVO;
import com.starnft.star.domain.article.repository.IUserThemeRepository;
import com.starnft.star.infrastructure.mapper.user.StarNftUserThemeMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserThemeRepository implements IUserThemeRepository,PageHelperInterface {
    @Resource
    StarNftUserThemeMapper starNftUserThemeMapper;
    @Override
    public ResponsePageResult<UserThemeVO> queryUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq) {
        PageInfo<UserThemeVO> result = PageHelper
                .startPage(userHaveSeriesReq.getPage(), userHaveSeriesReq.getSize())
                .doSelectPageInfo(() -> starNftUserThemeMapper.selectUserThemeToSeriesByUserId(userHaveSeriesReq));
            return listReplace(result,result.getList());
    }
}
