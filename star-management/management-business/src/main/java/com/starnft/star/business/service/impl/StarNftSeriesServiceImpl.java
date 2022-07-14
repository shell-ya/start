package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftSeries;
import com.starnft.star.business.mapper.StarNftSeriesMapper;
import com.starnft.star.business.service.IStarNftSeriesService;
import com.starnft.star.common.utils.SnowflakeWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 系列Service业务层处理
 *
 * @author shellya
 * @date 2022-05-27
 */
@Service
public class StarNftSeriesServiceImpl implements IStarNftSeriesService
{
    @Autowired
    private StarNftSeriesMapper starNftSeriesMapper;

    /**
     * 查询系列
     *
     * @param id 系列主键
     * @return 系列
     */
    @Override
    public StarNftSeries selectStarNftSeriesById(Long id)
    {
        return starNftSeriesMapper.selectStarNftSeriesById(id);
    }

    /**
     * 查询系列列表
     *
     * @param starNftSeries 系列
     * @return 系列
     */
    @Override
    public List<StarNftSeries> selectStarNftSeriesList(StarNftSeries starNftSeries)
    {
        return starNftSeriesMapper.selectStarNftSeriesList(starNftSeries);
    }

    /**
     * 新增系列
     *
     * @param starNftSeries 系列
     * @return 结果
     */
    @Override
    public int insertStarNftSeries(StarNftSeries starNftSeries, Long userId)
    {


        Long seriesId = SnowflakeWorker.generateId();
        starNftSeries.setCreateBy(userId.toString());
        starNftSeries.setUpdateBy(userId.toString());
        starNftSeries.setCreateAt(new Date());
        starNftSeries.setUpdateAt(new Date());
        starNftSeries.setId(seriesId);
        return starNftSeriesMapper.insertStarNftSeries(starNftSeries);
    }

    /**
     * 修改系列
     *
     * @param starNftSeries 系列
     * @return 结果
     */
    @Override
    public int updateStarNftSeries(StarNftSeries starNftSeries)
    {
        return starNftSeriesMapper.updateStarNftSeries(starNftSeries);
    }

    /**
     * 批量删除系列
     *
     * @param ids 需要删除的系列主键
     * @return 结果
     */
    @Override
    public int deleteStarNftSeriesByIds(Long[] ids)
    {
        return starNftSeriesMapper.deleteStarNftSeriesByIds(ids);
    }

    /**
     * 删除系列信息
     *
     * @param id 系列主键
     * @return 结果
     */
    @Override
    public int deleteStarNftSeriesById(Long id)
    {
        return starNftSeriesMapper.deleteStarNftSeriesById(id);
    }
}
