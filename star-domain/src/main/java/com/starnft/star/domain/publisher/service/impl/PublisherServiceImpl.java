package com.starnft.star.domain.publisher.service.impl;

import com.starnft.star.domain.publisher.model.req.PublisherReq;
import com.starnft.star.domain.publisher.model.vo.PublisherVO;
import com.starnft.star.domain.publisher.repository.IPublisherRepository;
import com.starnft.star.domain.publisher.service.PublisherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PublisherServiceImpl implements PublisherService {
    @Resource
    IPublisherRepository publisherRepository;
    @Override
    public PublisherVO queryPublisher(PublisherReq req) {
        return publisherRepository.queryPublisher(req);
    }
}
