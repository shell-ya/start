package com.starnft.star.infrastructure.mapper.activity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.infrastructure.entity.activity.GoodsHavingTimeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodsHavingTimeRecordMapper extends BaseMapper<GoodsHavingTimeRecord> {

    int addCountTimes(@Param("uid") Long uid, @Param("themeId") Long themeId, @Param("version") Integer version);
}
