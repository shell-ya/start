package com.starnft.star.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.business.domain.WhiteListConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WhiteListConfigMapper extends BaseMapper<WhiteListConfig> {

    WhiteListConfig queryByType(Integer whiteType);

    WhiteListConfig queryByGoodsId(Long goodsId);

    WhiteListConfig queryById(Long id);



}
