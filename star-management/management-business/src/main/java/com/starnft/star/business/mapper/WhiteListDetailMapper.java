package com.starnft.star.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.business.domain.WhiteListDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WhiteListDetailMapper extends BaseMapper<WhiteListDetail> {

    WhiteListDetail selectMappingWhite(@Param("whiteId") Long whiteId, @Param("uid") Long uid);

    Integer modifySurplus(@Param("whiteId") Long whiteId, @Param("uid") Long uid, @Param("version") Integer version);

    Integer addSurplus(@Param("whiteId") Long whiteId, @Param("uid") Long uid,@Param("surplusTime") Integer surplusTime,@Param("version") Integer version);

    Integer insertSurplus(WhiteListDetail whiteListDetail);
}
