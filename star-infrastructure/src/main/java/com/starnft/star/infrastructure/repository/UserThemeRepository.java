package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveNumbersReq;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.req.UserHaveThemeReq;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.model.vo.UserSeriesVO;
import com.starnft.star.domain.article.model.vo.UserThemeDetailVo;
import com.starnft.star.domain.article.model.vo.UserThemeVO;
import com.starnft.star.domain.article.repository.IUserThemeRepository;
import com.starnft.star.infrastructure.entity.user.StarNftUserTheme;
import com.starnft.star.infrastructure.mapper.user.StarNftUserThemeMapper;
import com.starnft.star.infrastructure.tools.PageHelperInterface;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Repository
public class UserThemeRepository implements IUserThemeRepository, PageHelperInterface {
    @Resource
    StarNftUserThemeMapper starNftUserThemeMapper;

    @Override
    public ResponsePageResult<UserSeriesVO> queryUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq) {
        PageInfo<UserSeriesVO> result = PageHelper
                .startPage(userHaveSeriesReq.getPage(), userHaveSeriesReq.getSize())
                .doSelectPageInfo(() -> this.starNftUserThemeMapper.selectUserThemeToSeriesByUserId(userHaveSeriesReq));
        return this.listReplace(result, result.getList());
    }

    @Override
    public ResponsePageResult<UserThemeVO> queryUserArticleThemeInfo(UserHaveThemeReq userHaveSeriesReq) {
        PageInfo<UserThemeVO> result = PageHelper
                .startPage(userHaveSeriesReq.getPage(), userHaveSeriesReq.getSize())
                .doSelectPageInfo(() -> this.starNftUserThemeMapper.selectUserThemeToThemeByUserId(userHaveSeriesReq));
        return this.listReplace(result, result.getList());
    }

    @Override
    public ResponsePageResult<UserNumbersVO> queryUserArticleNumberInfo(UserHaveNumbersReq userHaveNumbersReq) {
        PageInfo<UserNumbersVO> result = PageHelper
                .startPage(userHaveNumbersReq.getPage(), userHaveNumbersReq.getSize())
                .doSelectPageInfo(() -> this.starNftUserThemeMapper.selectUserThemeToNumbersByUserId(userHaveNumbersReq));
        return this.listReplace(result, result.getList());
    }

    @Override
    public UserNumbersVO queryUserNumberInfo(Long uid, Long numberId,UserNumberStatusEnum statusEnum) {
        StarNftUserTheme starNftUserTheme = this.starNftUserThemeMapper.selectOne(Wrappers.lambdaQuery(StarNftUserTheme.class)
                .eq(StarNftUserTheme::getUserId, uid)
                .eq(StarNftUserTheme::getSeriesThemeId, numberId)
                .eq(Objects.nonNull(statusEnum),StarNftUserTheme::getStatus, statusEnum.getCode()));
        if (Objects.isNull(starNftUserTheme)) {
            return null;
        }
        return UserNumbersVO.builder()
                .themeId(starNftUserTheme.getSeriesThemeInfoId())
                .numberId(starNftUserTheme.getSeriesThemeId())
                .price(starNftUserTheme.getPreTaxPrice())
                .status(starNftUserTheme.getStatus())
                .build();
    }

    @Override
    public Boolean modifyUserNumberStatus(Long uid, Long numberId, UserNumberStatusEnum beforeStatusEnum,UserNumberStatusEnum statusEnum) {
        return this.starNftUserThemeMapper.update(StarNftUserTheme.builder()
                        .status(statusEnum.getCode())
                        .updateAt(new Date())
                        .updateBy(String.valueOf(uid))
                        .build(),
                Wrappers.lambdaUpdate(StarNftUserTheme.class)
                        .eq(StarNftUserTheme::getSeriesThemeId, numberId)
                        .eq(StarNftUserTheme::getUserId, uid)
                        .eq(StarNftUserTheme::getStatus,beforeStatusEnum.getCode())) == 1;
    }


}
