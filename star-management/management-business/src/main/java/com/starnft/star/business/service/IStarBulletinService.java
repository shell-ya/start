package com.starnft.star.business.service;

import java.util.List;
import com.starnft.star.business.domain.StarBulletin;

/**
 * 公告Service接口
 * 
 * @author shellya
 * @date 2022-07-25
 */
public interface IStarBulletinService 
{
    /**
     * 查询公告
     * 
     * @param id 公告主键
     * @return 公告
     */
    public StarBulletin selectStarBulletinById(Long id);

    /**
     * 查询公告列表
     * 
     * @param starBulletin 公告
     * @return 公告集合
     */
    public List<StarBulletin> selectStarBulletinList(StarBulletin starBulletin);

    /**
     * 新增公告
     * 
     * @param starBulletin 公告
     * @return 结果
     */
    public int insertStarBulletin(StarBulletin starBulletin);

    /**
     * 修改公告
     * 
     * @param starBulletin 公告
     * @return 结果
     */
    public int updateStarBulletin(StarBulletin starBulletin);

    /**
     * 批量删除公告
     * 
     * @param ids 需要删除的公告主键集合
     * @return 结果
     */
    public int deleteStarBulletinByIds(Long[] ids);

    /**
     * 删除公告信息
     * 
     * @param id 公告主键
     * @return 结果
     */
    public int deleteStarBulletinById(Long id);
}
