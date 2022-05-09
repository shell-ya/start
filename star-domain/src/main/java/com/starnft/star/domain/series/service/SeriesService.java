package com.starnft.star.domain.series.service;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.series.model.req.SeriesReq;
import com.starnft.star.domain.series.model.vo.SeriesVO;

public interface SeriesService {
    ResponsePageResult<SeriesVO>  queryMainSeriesInfo(SeriesReq requestPage);
}
