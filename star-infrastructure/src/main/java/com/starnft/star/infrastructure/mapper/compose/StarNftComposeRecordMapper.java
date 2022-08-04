package com.starnft.star.infrastructure.mapper.compose;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.infrastructure.entity.compose.StarNftComposeRecord;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StarNftComposeRecordMapper extends BaseMapper<StarNftComposeRecord> {
    int updateBatch(List<StarNftComposeRecord> list);

    int updateBatchSelective(List<StarNftComposeRecord> list);

    int batchInsert(@Param("list") List<StarNftComposeRecord> list);

    int insertOrUpdate(StarNftComposeRecord record);

    int insertOrUpdateSelective(StarNftComposeRecord record);
}