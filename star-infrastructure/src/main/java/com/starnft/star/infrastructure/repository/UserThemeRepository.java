package com.starnft.star.infrastructure.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.model.vo.UserSeriesVO;
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
    public ResponsePageResult<UserSeriesVO> queryUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq) {
        PageInfo<UserSeriesVO> result = PageHelper
                .startPage(userHaveSeriesReq.getPage(), userHaveSeriesReq.getSize())
                .doSelectPageInfo(() -> starNftUserThemeMapper.selectUserThemeToSeriesByUserId(userHaveSeriesReq));
            return listReplace(result,result.getList());
    }

    @Override
    public ResponsePageResult<UserThemeVO> queryUserArticleThemeInfo(UserHaveSeriesReq userHaveSeriesReq) {
        PageInfo<UserThemeVO> result = PageHelper
                .startPage(userHaveSeriesReq.getPage(), userHaveSeriesReq.getSize())
                .doSelectPageInfo(() -> starNftUserThemeMapper.selectUserThemeToSeriesByUserId(userHaveSeriesReq));
        return listReplace(result,result.getList());
    }

    @Override
    public ResponsePageResult<UserNumbersVO> queryUserArticleNumberInfo(UserHaveSeriesReq userHaveSeriesReq) {
        return null;
    }


}
