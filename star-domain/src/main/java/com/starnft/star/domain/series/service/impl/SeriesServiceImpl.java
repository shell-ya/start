package com.starnft.star.domain.series.service.impl;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.series.model.req.SeriesReq;
import com.starnft.star.domain.series.model.vo.SeriesVO;
import com.starnft.star.domain.series.repository.ISeriesRepository;
import com.starnft.star.domain.series.service.SeriesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class SeriesServiceImpl implements SeriesService {
    @Resource
    ISeriesRepository iSeriesRepository;
    @Override
    public ResponsePageResult<SeriesVO> queryMainSeriesInfo(SeriesReq requestPage) {
        return iSeriesRepository.querySeries(requestPage);
    }
}
