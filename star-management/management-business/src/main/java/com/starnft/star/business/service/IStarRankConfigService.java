package com.starnft.star.business.service;

import com.starnft.star.business.domain.StarRankConfig;

import java.util.List;

/**
 * 排行榜Service接口
 *
 * @author shellya
 * @date 2022-06-28
 */
public interface IStarRankConfigService
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
    public Boolean insertStarRankConfig(StarRankConfig starRankConfig);

    /**
     * 修改排行榜
     *
     * @param starRankConfig 排行榜
     * @return 结果
     */
    public int updateStarRankConfig(StarRankConfig starRankConfig);

    /**
     * 批量删除排行榜
     *
     * @param ids 需要删除的排行榜主键集合
     * @return 结果
     */
    public int deleteStarRankConfigByIds(Long[] ids);

    /**
     * 删除排行榜信息
     *
     * @param id 排行榜主键
     * @return 结果
     */
    public int deleteStarRankConfigById(Long id);
}
