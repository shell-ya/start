package com.starnft.star.infrastructure.repository;

import com.starnft.star.domain.publisher.model.req.PublisherReq;
import com.starnft.star.domain.publisher.model.vo.PublisherVO;
import com.starnft.star.domain.publisher.repository.IPublisherRepository;
import com.starnft.star.infrastructure.mapper.publisher.StarNftPublisherMapper;
import org.apache.commons.collections4.SetUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class PublisherRepository implements IPublisherRepository {
    @Resource
    StarNftPublisherMapper starNftPublisherMapper;
    @Override
    public PublisherVO queryPublisher(PublisherReq publisherReq) {
        Optional.ofNullable(publisherReq.getPublisherId()).orElseThrow(()-> new RuntimeException("发行商ID不能为空"));
        return starNftPublisherMapper.queryPublisherById(publisherReq.getPublisherId());
    }

    @Override
    public List<PublisherVO> queryPublisherByIds(Set<Long> ids) {
        return starNftPublisherMapper.queryPublisherByIds(ids);
    }

    @Override
    public List<PublisherVO> queryPublisherByIdList(List<Long> idList) {
        return starNftPublisherMapper.queryPublisherByIdList(idList);
    }
}
