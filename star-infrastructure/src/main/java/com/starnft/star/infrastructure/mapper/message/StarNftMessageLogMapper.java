package com.starnft.star.infrastructure.mapper.message;

import com.starnft.star.infrastructure.entity.message.StarNftMessageLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StarNftMessageLogMapper {

    Integer insert(StarNftMessageLog starNftMessageLog);

}
