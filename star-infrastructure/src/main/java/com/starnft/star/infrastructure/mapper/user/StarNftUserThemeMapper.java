package com.starnft.star.infrastructure.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.vo.UserThemeVO;
import com.starnft.star.infrastructure.entity.user.StarNftUserTheme;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StarNftUserThemeMapper extends BaseMapper<StarNftUserTheme> {

    List<UserThemeVO> selectUserThemeToSeriesByUserId(UserHaveSeriesReq userHaveSeriesReq);
}