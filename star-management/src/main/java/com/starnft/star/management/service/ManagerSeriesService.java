package com.starnft.star.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.infrastructure.entity.series.StarNftSeries;

public interface ManagerSeriesService extends IService<StarNftSeries> {
   PageInfo<StarNftSeries> querySeries(RequestConditionPage<StarNftSeries> page);
   Boolean insertSeries(StarNftSeries series);
   Boolean updateSeries(StarNftSeries series);
   Boolean  deleteSeries(Long id);
   StarNftSeries detailSeries(Long id);

}
