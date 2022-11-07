package com.starnft.star.infrastructure.mapper.publisher;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.domain.publisher.model.vo.PublisherVO;
import com.starnft.star.infrastructure.entity.publisher.StarNftPublisher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface StarNftPublisherMapper extends BaseMapper<StarNftPublisher> {
    PublisherVO queryPublisherById(@Param("id") Long id);

    List<PublisherVO> queryPublisherByIds(@Param("ids") Set<Long> ids);


    List<PublisherVO> queryPublisherByIdList(List<Long> idList);
}