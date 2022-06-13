package com.starnft.star.domain.publisher.service;

import com.starnft.star.domain.publisher.model.req.PublisherReq;
import com.starnft.star.domain.publisher.model.vo.PublisherVO;

public interface PublisherService {
 PublisherVO queryPublisher(PublisherReq id);
}
