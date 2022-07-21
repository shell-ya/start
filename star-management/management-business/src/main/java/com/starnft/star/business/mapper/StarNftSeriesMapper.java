package com.starnft.star.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.business.domain.StarNftSeries;

import java.util.List;

/**
 * 系列Mapper接口
 *
 * @author shellya
 * @date 2022-05-27
 */
public interface StarNftSeriesMapper extends BaseMapper<StarNftSeries>
{
    /**
     * 查询系列
     *
     * @param id 系列主键
     * @return 系列
     */
    public StarNftSeries selectStarNftSeriesById(Long id);

    /**
     * 查询系列列表
     *
     * @param starNftSeries 系列
     * @return 系列集合
     */
    public List<StarNftSeries> selectStarNftSeriesList(StarNftSeries starNftSeries);

    /**
     * 新增系列
     *
     * @param starNftSeries 系列
     * @return 结果
     */
    public int insertStarNftSeries(StarNftSeries starNftSeries);

    /**
     * 修改系列
     *
     * @param starNftSeries 系列
     * @return 结果
     */
    public int updateStarNftSeries(StarNftSeries starNftSeries);

    /**
     * 删除系列
     *
     * @param id 系列主键
     * @return 结果
     */
    public int deleteStarNftSeriesById(Long id);

    /**
     * 批量删除系列
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftSeriesByIds(Long[] ids);
}
