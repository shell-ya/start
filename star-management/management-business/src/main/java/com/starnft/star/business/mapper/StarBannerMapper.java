package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.StarBanner;

import java.util.List;

/**
 * 轮播图Mapper接口
 *
 * @author shellya
 * @date 2022-06-10
 */
public interface StarBannerMapper
{
    /**
     * 查询轮播图
     *
     * @param id 轮播图主键
     * @return 轮播图
     */
    public StarBanner selectStarBannerById(Long id);

    /**
     * 查询轮播图列表
     *
     * @param starBanner 轮播图
     * @return 轮播图集合
     */
    public List<StarBanner> selectStarBannerList(StarBanner starBanner);

    /**
     * 新增轮播图
     *
     * @param starBanner 轮播图
     * @return 结果
     */
    public int insertStarBanner(StarBanner starBanner);

    /**
     * 修改轮播图
     *
     * @param starBanner 轮播图
     * @return 结果
     */
    public int updateStarBanner(StarBanner starBanner);

    /**
     * 删除轮播图
     *
     * @param id 轮播图主键
     * @return 结果
     */
    public int deleteStarBannerById(Long id);

    /**
     * 批量删除轮播图
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarBannerByIds(Long[] ids);
}
