package com.starnft.star.infrastructure.mapper.number;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.domain.number.model.dto.NumberDTO;
import com.starnft.star.domain.number.model.dto.NumberQueryDTO;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberDingVO;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.number.model.vo.ThemeNumberVo;
import com.starnft.star.infrastructure.entity.number.StarNftThemeNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Objects;

@Mapper
public interface StarNftThemeNumberMapper extends BaseMapper<StarNftThemeNumber> {
    NumberDetailVO selectNumberDetailById(@Param("id") Long id);

    List<NumberVO> selectNumberList(NumberQueryDTO numberQuery);

    ThemeNumberVo selectConsignThemeNumberDetail(NumberDTO dto);
    ThemeNumberVo selectRandomThemeNumber(Long id);
    List<NumberVO>  getNumberListByThemeInfo(NumberQueryDTO numberQueryDTO);
   List<NumberDingVO> getNumbers2Ding();
}
