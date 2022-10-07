package com.starnft.star.business.service.impl;

import java.util.Date;
import java.util.List;

import com.starnft.star.common.utils.SnowflakeWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.starnft.star.business.mapper.StarDingBulletinMapper;
import com.starnft.star.business.domain.StarDingBulletin;
import com.starnft.star.business.service.IStarDingBulletinService;

/**
 * 盯链公告Service业务层处理
 *
 * @author zz
 * @date 2022-10-03
 */
@Service
public class StarDingBulletinServiceImpl implements IStarDingBulletinService
{
    @Autowired
    private StarDingBulletinMapper starDingBulletinMapper;

    /**
     * 查询盯链公告
     *
     * @param id 盯链公告主键
     * @return 盯链公告
     */
    @Override
    public StarDingBulletin selectStarDingBulletinById(Long id)
    {
        return starDingBulletinMapper.selectStarDingBulletinById(id);
    }

    /**
     * 查询盯链公告列表
     *
     * @param starDingBulletin 盯链公告
     * @return 盯链公告
     */
    @Override
    public List<StarDingBulletin> selectStarDingBulletinList(StarDingBulletin starDingBulletin)
    {
        return starDingBulletinMapper.selectStarDingBulletinList(starDingBulletin);
    }

    /**
     * 新增盯链公告
     *
     * @param starDingBulletin 盯链公告
     * @return 结果
     */
    @Override
    public int insertStarDingBulletin(StarDingBulletin starDingBulletin)
    {
        starDingBulletin.setId(SnowflakeWorker.generateId());
        starDingBulletin.setUpdatedAt(new Date());
        starDingBulletin.setCreatedAt(new Date());
        starDingBulletin.setIsDeleted(0);
        return starDingBulletinMapper.insertStarDingBulletin(starDingBulletin);
    }

    /**
     * 修改盯链公告
     *
     * @param starDingBulletin 盯链公告
     * @return 结果
     */
    @Override
    public int updateStarDingBulletin(StarDingBulletin starDingBulletin)
    {
        starDingBulletin.setUpdatedAt(new Date());
        return starDingBulletinMapper.updateStarDingBulletin(starDingBulletin);
    }

    /**
     * 批量删除盯链公告
     *
     * @param ids 需要删除的盯链公告主键
     * @return 结果
     */
    @Override
    public int deleteStarDingBulletinByIds(Long[] ids)
    {
        return starDingBulletinMapper.deleteStarDingBulletinByIds(ids);
    }

    /**
     * 删除盯链公告信息
     *
     * @param id 盯链公告主键
     * @return 结果
     */
    @Override
    public int deleteStarDingBulletinById(Long id)
    {
        return starDingBulletinMapper.deleteStarDingBulletinById(id);
    }
}
