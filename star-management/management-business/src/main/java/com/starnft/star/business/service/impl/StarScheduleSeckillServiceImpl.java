package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarScheduleSeckill;
import com.starnft.star.business.domain.vo.StarScheduleSeckillVo;
import com.starnft.star.business.mapper.StarScheduleSeckillMapper;
import com.starnft.star.business.service.IStarScheduleSeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 秒杀活动Service业务层处理
 *
 * @author shellya
 * @date 2022-06-26
 */
@Service
public class StarScheduleSeckillServiceImpl implements IStarScheduleSeckillService
{
    @Autowired
    private StarScheduleSeckillMapper starScheduleSeckillMapper;

    /**
     * 查询秒杀活动
     *
     * @param id 秒杀活动主键
     * @return 秒杀活动
     */
    @Override
    public StarScheduleSeckill selectStarScheduleSeckillById(Long id)
    {
        return starScheduleSeckillMapper.selectStarScheduleSeckillById(id);
    }

    /**
     * 查询秒杀活动列表
     *
     * @param starScheduleSeckill 秒杀活动
     * @return 秒杀活动
     */
    @Override
    public List<StarScheduleSeckill> selectStarScheduleSeckillList(StarScheduleSeckill starScheduleSeckill)
    {
        return starScheduleSeckillMapper.selectStarScheduleSeckillList(starScheduleSeckill);
    }

    @Override
    public List<StarScheduleSeckillVo> selectStarScheduleSeckillVoList(StarScheduleSeckill starScheduleSeckill) {
        return starScheduleSeckillMapper.selectStarScheduleSeckillVoList(starScheduleSeckill);
    }

    /**
     * 新增秒杀活动
     *
     * @param starScheduleSeckill 秒杀活动
     * @return 结果
     */
    @Override
    public int insertStarScheduleSeckill(StarScheduleSeckill starScheduleSeckill,Long userId)
    {
        starScheduleSeckill.setCreatedAt(new Date());
        starScheduleSeckill.setModifiedAt(new Date());
        starScheduleSeckill.setCreatedBy(String.valueOf(userId));
        return starScheduleSeckillMapper.insertStarScheduleSeckill(starScheduleSeckill);
    }

    /**
     * 修改秒杀活动
     *
     * @param starScheduleSeckill 秒杀活动
     * @return 结果
     */
    @Override
    public int updateStarScheduleSeckill(StarScheduleSeckill starScheduleSeckill)
    {
        return starScheduleSeckillMapper.updateStarScheduleSeckill(starScheduleSeckill);
    }

    /**
     * 批量删除秒杀活动
     *
     * @param ids 需要删除的秒杀活动主键
     * @return 结果
     */
    @Override
    public int deleteStarScheduleSeckillByIds(Long[] ids)
    {
        return starScheduleSeckillMapper.deleteStarScheduleSeckillByIds(ids);
    }

    /**
     * 删除秒杀活动信息
     *
     * @param id 秒杀活动主键
     * @return 结果
     */
    @Override
    public int deleteStarScheduleSeckillById(Long id)
    {
        return starScheduleSeckillMapper.deleteStarScheduleSeckillById(id);
    }
}
