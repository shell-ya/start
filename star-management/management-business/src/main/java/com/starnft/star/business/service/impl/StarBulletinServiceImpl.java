package com.starnft.star.business.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.starnft.star.business.mapper.StarBulletinMapper;
import com.starnft.star.business.domain.StarBulletin;
import com.starnft.star.business.service.IStarBulletinService;

/**
 * 公告Service业务层处理
 *
 * @author shellya
 * @date 2022-07-25
 */
@Service
public class StarBulletinServiceImpl implements IStarBulletinService
{
    @Autowired
    private StarBulletinMapper starBulletinMapper;

    /**
     * 查询公告
     *
     * @param id 公告主键
     * @return 公告
     */
    @Override
    public StarBulletin selectStarBulletinById(Long id)
    {
        return starBulletinMapper.selectStarBulletinById(id);
    }

    /**
     * 查询公告列表
     *
     * @param starBulletin 公告
     * @return 公告
     */
    @Override
    public List<StarBulletin> selectStarBulletinList(StarBulletin starBulletin)
    {
        return starBulletinMapper.selectStarBulletinList(starBulletin);
    }

    /**
     * 新增公告
     *
     * @param starBulletin 公告
     * @return 结果
     */
    @Override
    public int insertStarBulletin(StarBulletin starBulletin)
    {

        starBulletin.setIsDeleted(0);
        starBulletin.setPublishTime(new Date());
        starBulletin.setCreatedAt(new Date());
        starBulletin.setModifiedAt(new Date());
        return starBulletinMapper.insert(starBulletin);
    }

    /**
     * 修改公告
     *
     * @param starBulletin 公告
     * @return 结果
     */
    @Override
    public int updateStarBulletin(StarBulletin starBulletin)
    {
        return starBulletinMapper.updateStarBulletin(starBulletin);
    }

    /**
     * 批量删除公告
     *
     * @param ids 需要删除的公告主键
     * @return 结果
     */
    @Override
    public int deleteStarBulletinByIds(Long[] ids)
    {
        return starBulletinMapper.deleteStarBulletinByIds(ids);
    }

    /**
     * 删除公告信息
     *
     * @param id 公告主键
     * @return 结果
     */
    @Override
    public int deleteStarBulletinById(Long id)
    {
        return starBulletinMapper.deleteStarBulletinById(id);
    }
}
