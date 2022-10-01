package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.DrawActivity;

import java.util.List;

/**
 * 活动配置Mapper接口
 *
 * @author zz
 * @date 2022-09-28
 */
public interface DrawActivityMapper
{
    /**
     * 查询活动配置
     *
     * @param id 活动配置主键
     * @return 活动配置
     */
    public DrawActivity selectDrawActivityById(Long id);

    /**
     * 查询活动配置列表
     *
     * @param drawActivity 活动配置
     * @return 活动配置集合
     */
    public List<DrawActivity> selectDrawActivityList(DrawActivity drawActivity);

    /**
     * 新增活动配置
     *
     * @param drawActivity 活动配置
     * @return 结果
     */
    public int insertDrawActivity(DrawActivity drawActivity);

    /**
     * 修改活动配置
     *
     * @param drawActivity 活动配置
     * @return 结果
     */
    public int updateDrawActivity(DrawActivity drawActivity);

    /**
     * 删除活动配置
     *
     * @param id 活动配置主键
     * @return 结果
     */
    public int deleteDrawActivityById(Long id);

    /**
     * 批量删除活动配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDrawActivityByIds(Long[] ids);
}
