package com.starnft.star.infrastructure.mapper.number;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.domain.number.model.dto.NumberQueryDTO;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.infrastructure.entity.number.StarNftThemeNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StarNftThemeNumberMapper extends BaseMapper<StarNftThemeNumber> {
    NumberDetailVO selectNumberDetailId(@Param("id") Long id);

    List<NumberVO> selectNumberList(NumberQueryDTO numberQuery);
}
