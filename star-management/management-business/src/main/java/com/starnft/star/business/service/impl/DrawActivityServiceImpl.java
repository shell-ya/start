package com.starnft.star.business.service.impl;

import java.util.List;

import com.starnft.star.business.domain.DrawActivity;
import com.starnft.star.business.mapper.DrawActivityMapper;
import com.starnft.star.business.service.IDrawActivityService;
import com.starnft.star.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 活动配置Service业务层处理
 *
 * @author zz
 * @date 2022-09-28
 */
@Service
public class DrawActivityServiceImpl implements IDrawActivityService
{
    @Autowired
    private DrawActivityMapper drawActivityMapper;

    /**
     * 查询活动配置
     *
     * @param id 活动配置主键
     * @return 活动配置
     */
    @Override
    public DrawActivity selectDrawActivityById(Long id)
    {
        return drawActivityMapper.selectDrawActivityById(id);
    }

    /**
     * 查询活动配置列表
     *
     * @param drawActivity 活动配置
     * @return 活动配置
     */
    @Override
    public List<DrawActivity> selectDrawActivityList(DrawActivity drawActivity)
    {
        return drawActivityMapper.selectDrawActivityList(drawActivity);
    }

    /**
     * 新增活动配置
     *
     * @param drawActivity 活动配置
     * @return 结果
     */
    @Override
    public int insertDrawActivity(DrawActivity drawActivity)
    {
        drawActivity.setCreateTime(DateUtils.getNowDate());
        return drawActivityMapper.insertDrawActivity(drawActivity);
    }

    /**
     * 修改活动配置
     *
     * @param drawActivity 活动配置
     * @return 结果
     */
    @Override
    public int updateDrawActivity(DrawActivity drawActivity)
    {
        drawActivity.setUpdateTime(DateUtils.getNowDate());
        return drawActivityMapper.updateDrawActivity(drawActivity);
    }

    /**
     * 批量删除活动配置
     *
     * @param ids 需要删除的活动配置主键
     * @return 结果
     */
    @Override
    public int deleteDrawActivityByIds(Long[] ids)
    {
        return drawActivityMapper.deleteDrawActivityByIds(ids);
    }

    /**
     * 删除活动配置信息
     *
     * @param id 活动配置主键
     * @return 结果
     */
    @Override
    public int deleteDrawActivityById(Long id)
    {
        return drawActivityMapper.deleteDrawActivityById(id);
    }
}
