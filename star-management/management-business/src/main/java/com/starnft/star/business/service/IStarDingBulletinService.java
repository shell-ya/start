package com.starnft.star.business.service;

import java.util.List;
import com.starnft.star.business.domain.StarDingBulletin;

/**
 * 盯链公告Service接口
 * 
 * @author zz
 * @date 2022-10-03
 */
public interface IStarDingBulletinService 
{
    /**
     * 查询盯链公告
     * 
     * @param id 盯链公告主键
     * @return 盯链公告
     */
    public StarDingBulletin selectStarDingBulletinById(Long id);

    /**
     * 查询盯链公告列表
     * 
     * @param starDingBulletin 盯链公告
     * @return 盯链公告集合
     */
    public List<StarDingBulletin> selectStarDingBulletinList(StarDingBulletin starDingBulletin);

    /**
     * 新增盯链公告
     * 
     * @param starDingBulletin 盯链公告
     * @return 结果
     */
    public int insertStarDingBulletin(StarDingBulletin starDingBulletin);

    /**
     * 修改盯链公告
     * 
     * @param starDingBulletin 盯链公告
     * @return 结果
     */
    public int updateStarDingBulletin(StarDingBulletin starDingBulletin);

    /**
     * 批量删除盯链公告
     * 
     * @param ids 需要删除的盯链公告主键集合
     * @return 结果
     */
    public int deleteStarDingBulletinByIds(Long[] ids);

    /**
     * 删除盯链公告信息
     * 
     * @param id 盯链公告主键
     * @return 结果
     */
    public int deleteStarDingBulletinById(Long id);
}
