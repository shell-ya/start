package com.starnft.star.infrastructure.mapper.banner;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.domain.banner.model.req.BannerReq;
import com.starnft.star.infrastructure.entity.banner.BannerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date 2022/5/9 10:26 AM
 * @Author ： shellya
 */
@Mapper
public interface BannerMapper extends BaseMapper<BannerEntity> {

    List<BannerEntity> queryBanner(@Param("type") String type, @Param("size") int size);
}
