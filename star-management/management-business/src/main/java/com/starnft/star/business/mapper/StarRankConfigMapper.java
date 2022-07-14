package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.StarRankConfig;

import java.util.List;

/**
 * 排行榜Mapper接口
 *
 * @author shellya
 * @date 2022-06-28
 */
public interface StarRankConfigMapper
{
    /**
     * 查询排行榜
     *
     * @param id 排行榜主键
     * @return 排行榜
     */
    public StarRankConfig selectStarRankConfigById(Long id);

    /**
     * 查询排行榜列表
     *
     * @param starRankConfig 排行榜
     * @return 排行榜集合
     */
    public List<StarRankConfig> selectStarRankConfigList(StarRankConfig starRankConfig);

    /**
     * 新增排行榜
     *
     * @param starRankConfig 排行榜
     * @return 结果
     */
    public int insertStarRankConfig(StarRankConfig starRankConfig);

    /**
     * 修改排行榜
     *
     * @param starRankConfig 排行榜
     * @return 结果
     */
    public int updateStarRankConfig(StarRankConfig starRankConfig);

    /**
     * 删除排行榜
     *
     * @param id 排行榜主键
     * @return 结果
     */
    public int deleteStarRankConfigById(Long id);

    /**
     * 批量删除排行榜
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarRankConfigByIds(Long[] ids);
}
