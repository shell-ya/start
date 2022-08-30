package com.starnft.star.infrastructure.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.infrastructure.entity.user.WhiteListDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WhiteListDetailMapper extends BaseMapper<WhiteListDetail> {

    WhiteListDetail selectMappingWhite(@Param("whiteId") Long whiteId, @Param("uid") Long uid);

    Integer modifySurplus(@Param("whiteId") Long whiteId, @Param("uid") Long uid, @Param("version") Integer version);

}
