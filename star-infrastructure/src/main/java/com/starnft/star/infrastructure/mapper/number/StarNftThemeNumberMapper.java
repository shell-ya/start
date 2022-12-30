package com.starnft.star.infrastructure.mapper.number;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.domain.number.model.dto.NumberDTO;
import com.starnft.star.domain.number.model.dto.NumberQueryDTO;
import com.starnft.star.domain.number.model.req.MarketNumberListReq;
import com.starnft.star.domain.number.model.vo.*;
import com.starnft.star.infrastructure.entity.number.StarNftThemeNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface StarNftThemeNumberMapper extends BaseMapper<StarNftThemeNumber> {
    NumberDetailVO selectNumberDetailById(@Param("id") Long id);

    Integer queryCount();

    List<NumberVO> selectNumberList(NumberQueryDTO numberQuery);

    ThemeNumberVo selectConsignThemeNumberDetail(NumberDTO dto);
    ThemeNumberVo selectRandomThemeNumber(Long id);
    List<NumberVO>  getNumberListByThemeInfo(NumberQueryDTO numberQueryDTO);
   List<NumberDingVO> getNumbers2Ding();
   List<ThemeDingVo> getTheme2Ding();

   @Select("SELECT * from star_nft_theme_number as n    WHERE LENGTH(owner_by) > 10")
   List<StarNftThemeNumber> getBing();

    Boolean updateNumberStatus(@Param("id") Long id, @Param("userId")Long userId, @Param("code")Integer code, @Param("version")Integer version);

    Integer destroyedPublishNumber(Long themeId);

    BigDecimal minPrice(Long themeId);

    List<MarketNumberInfoVO> marketNumberList(MarketNumberListReq marketNumberListReq);

    BigDecimal avgPrice(Long themeId);

    List<BigDecimal> allPrice(@Param("themeId") Long themeId,@Param("median")BigDecimal median);

    List<RaisingTheme> nowRaisingTheme();

    List<ThemeNumberVo> minConsignNumber();

    List<ThemeNumberVo> consignNumberByTheme(Long themeId);

    boolean takeNumber(Long themeId);

    BigDecimal consignMinPrice(Long themeInfoId);
}
