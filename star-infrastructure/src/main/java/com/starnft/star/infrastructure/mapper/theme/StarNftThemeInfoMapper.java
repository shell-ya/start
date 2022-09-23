package com.starnft.star.infrastructure.mapper.theme;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.domain.theme.model.req.ThemeGoodsReq;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.vo.ThemeGoodsVO;
import com.starnft.star.domain.theme.model.vo.ThemeVO;
import com.starnft.star.infrastructure.entity.theme.StarNftThemeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StarNftThemeInfoMapper extends BaseMapper<StarNftThemeInfo> {
    List<ThemeVO> selectRecommendThemes(ThemeReq param);

    List<ThemeGoodsVO> themeGoodsList(ThemeGoodsReq req);
    Integer selectThemeIssuedQty(@Param("themeId") Long themeId);
}
