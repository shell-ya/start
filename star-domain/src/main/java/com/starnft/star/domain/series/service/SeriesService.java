package com.starnft.star.domain.series.service;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.series.model.req.SeriesReq;
import com.starnft.star.domain.series.model.vo.SeriesVO;

import java.util.List;

public interface SeriesService {
    ResponsePageResult<SeriesVO> queryMainSeriesInfo(SeriesReq requestPage);

    List<SeriesVO> querySeriesByType(Integer type);

    SeriesVO querySeriesById(Long id);
}
