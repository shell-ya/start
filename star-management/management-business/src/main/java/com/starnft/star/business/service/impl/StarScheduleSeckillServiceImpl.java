package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarScheduleSeckill;
import com.starnft.star.business.domain.vo.StarScheduleSeckillVo;
import com.starnft.star.business.mapper.StarScheduleSeckillMapper;
import com.starnft.star.business.service.IStarScheduleSeckillService;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.DateUtil;
import com.starnft.star.common.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 秒杀活动Service业务层处理
 *
 * @author shellya
 * @date 2022-06-26
 */
@Slf4j
@Service
public class StarScheduleSeckillServiceImpl implements IStarScheduleSeckillService {
    @Autowired
    private StarScheduleSeckillMapper starScheduleSeckillMapper;
    @Resource
    RedisUtil redisUtil;

    /**
     * 查询秒杀活动
     *
     * @param id 秒杀活动主键
     * @return 秒杀活动
     */
    @Override
    public StarScheduleSeckill selectStarScheduleSeckillById(Long id) {
        return starScheduleSeckillMapper.selectStarScheduleSeckillById(id);
    }

    /**
     * 查询秒杀活动列表
     *
     * @param starScheduleSeckill 秒杀活动
     * @return 秒杀活动
     */
    @Override
    public List<StarScheduleSeckill> selectStarScheduleSeckillList(StarScheduleSeckill starScheduleSeckill) {
        return starScheduleSeckillMapper.selectStarScheduleSeckillList(starScheduleSeckill);
    }

    @Override
    public List<StarScheduleSeckillVo> selectStarScheduleSeckillVoList(StarScheduleSeckill starScheduleSeckill) {
        List<StarScheduleSeckillVo> starScheduleSeckillVos = starScheduleSeckillMapper.selectStarScheduleSeckillVoList(starScheduleSeckill);
        for (StarScheduleSeckillVo killVo :
                starScheduleSeckillVos) {
            long poolSize = redisUtil.sGetSetSize(String.format(RedisKey.SECKILL_GOODS_STOCK_POOL.getKey(), killVo.getSpuId(), DateUtil.date2Str(killVo.getStartTime())));
            long queueSize = redisUtil.lGetListSize(String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), killVo.getSpuId(), DateUtil.date2Str(killVo.getStartTime())));
            killVo.setUnsoldNum((int) (poolSize + queueSize));
        }
        return starScheduleSeckillVos;
    }

    /**
     * 新增秒杀活动
     *
     * @param starScheduleSeckill 秒杀活动
     * @return 结果
     */
    @Override
    public int insertStarScheduleSeckill(StarScheduleSeckill starScheduleSeckill, Long userId) {
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
    public int updateStarScheduleSeckill(StarScheduleSeckill starScheduleSeckill) {
        return starScheduleSeckillMapper.updateStarScheduleSeckill(starScheduleSeckill);
    }

    /**
     * 批量删除秒杀活动
     *
     * @param ids 需要删除的秒杀活动主键
     * @return 结果
     */
    @Override
    public int deleteStarScheduleSeckillByIds(Long[] ids) {
        return starScheduleSeckillMapper.deleteStarScheduleSeckillByIds(ids);
    }

    /**
     * 删除秒杀活动信息
     *
     * @param id 秒杀活动主键
     * @return 结果
     */
    @Override
    public int deleteStarScheduleSeckillById(Long id) {
        return starScheduleSeckillMapper.deleteStarScheduleSeckillById(id);
    }

    @Override
    public int takeDownStock(Long id, Integer downType) {
        try {
            StarScheduleSeckill starScheduleSeckill = starScheduleSeckillMapper.selectStarScheduleSeckillById(id);
            if (Objects.isNull(starScheduleSeckill))
                throw new StarException(StarError.SYSTEM_ERROR, "秒杀活动ID不存在");

            redisUtil.delByKey(String.format(RedisKey.SECKILL_GOODS_STOCK_POOL.getKey(), starScheduleSeckill.getSpuId(), DateUtil.date2Str(starScheduleSeckill.getStartTime())));
            redisUtil.delByKey(String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), starScheduleSeckill.getSpuId(), DateUtil.date2Str(starScheduleSeckill.getStartTime())));
            redisUtil.hdel(RedisKey.SECKILL_GOODS_STOCK_NUMBER.getKey(), String.format("%s-time-%s", starScheduleSeckill.getSpuId(), DateUtil.date2Str(starScheduleSeckill.getStartTime())));
            if (downType == 2) {
//                redisUtil.hget()
                redisUtil.hdel(String.format(RedisKey.SECKILL_GOODS_INFO.getKey(), DateUtil.date2Str(starScheduleSeckill.getStartTime())),starScheduleSeckill.getSpuId());
            }
            return 1;
        } catch (Exception e) {
            log.error("下架藏品失败", e);
            throw new StarException(StarError.SYSTEM_ERROR, "下架藏品失败");
        }
    }


}
