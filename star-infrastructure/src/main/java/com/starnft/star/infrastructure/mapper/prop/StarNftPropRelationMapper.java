package com.starnft.star.infrastructure.mapper.prop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.infrastructure.entity.prop.StarNftPropRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StarNftPropRelationMapper extends BaseMapper<StarNftPropRelation> {

    StarNftPropRelation selectSpecific(@Param("uid") Long uid, @Param("propId") Long propId);

    Integer modifyPropsCounts(@Param("id") Long id, @Param("uid") Long uid, @Param("propId") Long propId, @Param("counts") Integer counts);
}
