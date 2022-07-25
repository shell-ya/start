package com.starnft.star.business.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.business.domain.StarBulletin;

/**
 * 公告Mapper接口
 *
 * @author shellya
 * @date 2022-07-25
 */
public interface StarBulletinMapper extends BaseMapper<StarBulletin>
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
     * 删除公告
     *
     * @param id 公告主键
     * @return 结果
     */
    public int deleteStarBulletinById(Long id);

    /**
     * 批量删除公告
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarBulletinByIds(Long[] ids);
}
