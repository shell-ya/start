package com.starnft.star.domain.series.service.impl;

import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.enums.CommodityTypeEnum;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.series.model.req.SeriesReq;
import com.starnft.star.domain.series.model.vo.SeriesVO;
import com.starnft.star.domain.series.repository.ISeriesRepository;
import com.starnft.star.domain.series.service.SeriesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SeriesServiceImpl implements SeriesService {
    @Resource
    ISeriesRepository seriesRepository;

    @Override
    public ResponsePageResult<SeriesVO> queryMainSeriesInfo(SeriesReq requestPage) {
        return this.seriesRepository.querySeries(requestPage);
    }

    @Override
    @Cached(name = StarConstants.SERIES_CACHE_NAME,
            expire = 3600 * 12,
            cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 3600 * 6, stopRefreshAfterLastAccess = 3600 * 3)
    @CachePenetrationProtect
    public List<SeriesVO> querySeriesByType(Integer type) {
        return this.seriesRepository.querySeries(CommodityTypeEnum.getCommodityTypeEnum(type));
    }
}
