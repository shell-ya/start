package com.starnft.star.domain.series.repository;

import com.starnft.star.common.enums.CommodityTypeEnum;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.series.model.req.SeriesReq;
import com.starnft.star.domain.series.model.vo.SeriesVO;

import java.util.List;

public interface ISeriesRepository {
    ResponsePageResult<SeriesVO> querySeries(SeriesReq requestPage);

    List<SeriesVO> querySeries(CommodityTypeEnum commodityType);

    SeriesVO querySeriesById(Long id);
}
