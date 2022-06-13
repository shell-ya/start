package com.starnft.star.domain.publisher.repository;

import com.starnft.star.domain.publisher.model.req.PublisherReq;
import com.starnft.star.domain.publisher.model.vo.PublisherVO;

import java.util.List;
import java.util.Set;

public interface IPublisherRepository {
    PublisherVO queryPublisher(PublisherReq publisherReq);

    List<PublisherVO> queryPublisherByIds(Set<Long> collect);

}
