package com.starnft.star.infrastructure.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.infrastructure.entity.user.WhiteListConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WhiteListConfigMapper extends BaseMapper<WhiteListConfig> {

    WhiteListConfig queryByType(Integer whiteType);

}
