package com.starnft.star.infrastructure.mapper.prop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.infrastructure.entity.prop.StarNftPropInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StarNftPropInfoMapper extends BaseMapper<StarNftPropInfo> {

    List<StarNftPropInfo> queryAll();

    List<StarNftPropInfo> queryShopList(@Param("onSell") Integer onSell);
}
