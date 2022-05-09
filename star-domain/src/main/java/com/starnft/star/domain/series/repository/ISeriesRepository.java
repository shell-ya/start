package com.starnft.star.domain.series.repository;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.series.model.req.SeriesReq;
import com.starnft.star.domain.series.model.vo.SeriesVO;

public interface ISeriesRepository {
    ResponsePageResult<SeriesVO> querySeries(SeriesReq requestPage);
}
