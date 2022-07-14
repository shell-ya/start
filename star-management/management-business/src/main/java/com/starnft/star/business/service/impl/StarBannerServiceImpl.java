package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarBanner;
import com.starnft.star.business.mapper.StarBannerMapper;
import com.starnft.star.business.service.IStarBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图Service业务层处理
 *
 * @author shellya
 * @date 2022-06-10
 */
@Service
public class StarBannerServiceImpl implements IStarBannerService
{
    @Autowired
    private StarBannerMapper starBannerMapper;

    /**
     * 查询轮播图
     *
     * @param id 轮播图主键
     * @return 轮播图
     */
    @Override
    public StarBanner selectStarBannerById(Long id)
    {
        return starBannerMapper.selectStarBannerById(id);
    }

    /**
     * 查询轮播图列表
     *
     * @param starBanner 轮播图
     * @return 轮播图
     */
    @Override
    public List<StarBanner> selectStarBannerList(StarBanner starBanner)
    {
        return starBannerMapper.selectStarBannerList(starBanner);
    }

    /**
     * 新增轮播图
     *
     * @param starBanner 轮播图
     * @return 结果
     */
    @Override
    public int insertStarBanner(StarBanner starBanner)
    {
        return starBannerMapper.insertStarBanner(starBanner);
    }

    /**
     * 修改轮播图
     *
     * @param starBanner 轮播图
     * @return 结果
     */
    @Override
    public int updateStarBanner(StarBanner starBanner)
    {
        return starBannerMapper.updateStarBanner(starBanner);
    }

    /**
     * 批量删除轮播图
     *
     * @param ids 需要删除的轮播图主键
     * @return 结果
     */
    @Override
    public int deleteStarBannerByIds(Long[] ids)
    {
        return starBannerMapper.deleteStarBannerByIds(ids);
    }

    /**
     * 删除轮播图信息
     *
     * @param id 轮播图主键
     * @return 结果
     */
    @Override
    public int deleteStarBannerById(Long id)
    {
        return starBannerMapper.deleteStarBannerById(id);
    }
}
