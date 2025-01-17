package com.starnft.star.infrastructure.mapper.number;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.domain.number.model.dto.NumberCirculationAddDTO;
import com.starnft.star.infrastructure.entity.number.StarNftNumberCirculationHist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Harlan
 * @date 2022/05/26 15:43
 */
@Mapper
public interface StarNftNumberCirculationHistMapper extends BaseMapper<StarNftNumberCirculationHist> {


    void saveBatchNumberCirculationRecord(@Param("arrays") List<StarNftNumberCirculationHist>  numberCirculation);
}
