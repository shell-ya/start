package com.starnft.star.infrastructure.mapper.prop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.infrastructure.entity.prop.StarNftPropInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StarNftPropInfoMapper extends BaseMapper<StarNftPropInfo> {

    List<StarNftPropInfo> queryAll();
}
