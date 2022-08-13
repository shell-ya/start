package com.starnft.star.infrastructure.mapper.activity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.domain.activity.model.vo.ActivityVO;
import com.starnft.star.infrastructure.entity.activity.StarScheduleSeckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StarScheduleSeckillMapper extends BaseMapper<StarScheduleSeckill> {

    List<StarScheduleSeckill> obtainActivities(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("keys") List<String> keys);

    StarScheduleSeckill queryByThemeId(@Param("themeId") Integer themeId);

    Integer modifyStock(@Param("themeId") Integer themeId, @Param("stock") Integer stock, @Param("version") Integer version);

}
