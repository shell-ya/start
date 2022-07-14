package com.starnft.star.business.service;

import com.starnft.star.business.domain.StarScheduleSeckill;
import com.starnft.star.business.domain.vo.StarScheduleSeckillVo;

import java.util.List;

/**
 * 秒杀活动Service接口
 *
 * @author shellya
 * @date 2022-06-26
 */
public interface IStarScheduleSeckillService
{
    /**
     * 查询秒杀活动
     *
     * @param id 秒杀活动主键
     * @return 秒杀活动
     */
    public StarScheduleSeckill selectStarScheduleSeckillById(Long id);

    /**
     * 查询秒杀活动列表
     *
     * @param starScheduleSeckill 秒杀活动
     * @return 秒杀活动集合
     */
    public List<StarScheduleSeckill> selectStarScheduleSeckillList(StarScheduleSeckill starScheduleSeckill);



    public List<StarScheduleSeckillVo> selectStarScheduleSeckillVoList(StarScheduleSeckill starScheduleSeckill);

    /**
     * 新增秒杀活动
     *
     * @param starScheduleSeckill 秒杀活动
     * @return 结果
     */
    public int insertStarScheduleSeckill(StarScheduleSeckill starScheduleSeckill,Long userId);

    /**
     * 修改秒杀活动
     *
     * @param starScheduleSeckill 秒杀活动
     * @return 结果
     */
    public int updateStarScheduleSeckill(StarScheduleSeckill starScheduleSeckill);

    /**
     * 批量删除秒杀活动
     *
     * @param ids 需要删除的秒杀活动主键集合
     * @return 结果
     */
    public int deleteStarScheduleSeckillByIds(Long[] ids);

    /**
     * 删除秒杀活动信息
     *
     * @param id 秒杀活动主键
     * @return 结果
     */
    public int deleteStarScheduleSeckillById(Long id);
}
