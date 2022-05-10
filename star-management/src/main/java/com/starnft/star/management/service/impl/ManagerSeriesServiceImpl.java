package com.starnft.star.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.infrastructure.entity.series.StarNftSeries;
import com.starnft.star.infrastructure.mapper.series.StarNftSeriesMapper;
import com.starnft.star.management.service.ManagerSeriesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagerSeriesServiceImpl extends ServiceImpl<StarNftSeriesMapper, StarNftSeries> implements ManagerSeriesService {
    @Override
    public PageInfo<StarNftSeries> querySeries(RequestConditionPage<StarNftSeries> page) {
        return PageHelper.startPage(page.getPage(), page.getSize()).doSelectPageInfo(() -> {
            this.list(new QueryWrapper<StarNftSeries>().setEntity(page.getCondition()).
                    eq(StarNftSeries.COL_IS_DELETE, Boolean.FALSE));
        });
    }
    @Override
    @Transactional
    public Boolean insertSeries(StarNftSeries series) {
        return  this.save(series);
    }

    @Override
    @Transactional
    public Boolean updateSeries(StarNftSeries series) {
        return this.updateById(series);
    }

    @Override
    @Transactional
    public Boolean deleteSeries(Long id) {
        return this.updateById(StarNftSeries.builder().id(id).isDelete(Boolean.TRUE).build());
    }
    @Override
    public StarNftSeries detailSeries(Long id) {
      return  this.getById(id);
    }
}
