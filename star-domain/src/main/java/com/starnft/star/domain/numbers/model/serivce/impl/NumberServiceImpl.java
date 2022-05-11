package com.starnft.star.domain.numbers.model.serivce.impl;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.numbers.model.req.NumberReq;
import com.starnft.star.domain.numbers.model.serivce.NumberService;
import com.starnft.star.domain.numbers.model.vo.NumberVO;
import com.starnft.star.domain.numbers.repository.INumberRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NumberServiceImpl implements NumberService {
    @Resource
    INumberRepository iNumberRepository;
    @Override
    public ResponsePageResult<NumberVO> queryThemeNumber(NumberReq numberReq) {
        return iNumberRepository.queryNumber(numberReq);
    }
}
