package com.starnft.star.domain.publisher.service;

import com.starnft.star.domain.publisher.model.req.PublisherReq;
import com.starnft.star.domain.publisher.model.vo.PublisherVO;

import java.util.List;
import java.util.Set;

public interface PublisherService {
    PublisherVO queryPublisher(PublisherReq id);

    List<PublisherVO> queryPublisherByIds(Set<Long> collect);

}
