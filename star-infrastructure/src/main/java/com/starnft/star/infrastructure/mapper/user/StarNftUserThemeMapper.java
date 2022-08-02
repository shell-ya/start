package com.starnft.star.infrastructure.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.domain.article.model.req.UserHaveNumbersReq;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.req.UserHaveThemeReq;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.model.vo.UserSeriesVO;
import com.starnft.star.domain.article.model.vo.UserThemeDetailVo;
import com.starnft.star.domain.article.model.vo.UserThemeVO;
import com.starnft.star.domain.number.model.vo.UserThemeMappingVO;
import com.starnft.star.infrastructure.entity.user.StarNftUserTheme;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StarNftUserThemeMapper extends BaseMapper<StarNftUserTheme> {

    List<UserSeriesVO> selectUserThemeToSeriesByUserId(UserHaveSeriesReq userHaveSeriesReq);
    List<UserThemeVO> selectUserThemeToThemeByUserId(UserHaveThemeReq userHaveThemeReq);
    List<UserNumbersVO> selectUserThemeToNumbersByUserId(UserHaveNumbersReq userHaveNumbersReq);
    boolean updateUserThemeMapping(UserThemeMappingVO updateThemeMappingVo);

    List<UserNumbersVO> queryUserArticleNumberInfoByThemeIds(@Param("uid") Long uid, @Param("themeIds")List<Long> themeIds, @Param("statusEnum") UserNumberStatusEnum statusEnum);
    List<UserNumbersVO> queryUserArticleNumberInfoByNumberIds(@Param("uid") Long uid, @Param("numberIds")List<Long> themeIds, @Param("statusEnum") UserNumberStatusEnum statusEnum);
}
