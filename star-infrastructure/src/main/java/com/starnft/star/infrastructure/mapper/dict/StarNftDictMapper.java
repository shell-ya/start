package com.starnft.star.infrastructure.mapper.dict;

import com.starnft.star.infrastructure.entity.dict.StarNftDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StarNftDictMapper {

   int insertBatch(@Param("entities") List<StarNftDict> entities);

}
