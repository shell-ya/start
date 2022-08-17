package com.starnft.star.infrastructure.repository;

import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.activity.model.vo.ActivityVO;
import com.starnft.star.domain.activity.model.vo.DrawActivityVO;
import com.starnft.star.domain.activity.repository.IActivityRepository;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.draw.model.req.PartakeReq;
import com.starnft.star.infrastructure.entity.activity.StarScheduleSeckill;
import com.starnft.star.infrastructure.entity.draw.Activity;
import com.starnft.star.infrastructure.mapper.activity.StarScheduleSeckillMapper;
import com.starnft.star.infrastructure.mapper.draw.IActivityDao;
import com.starnft.star.infrastructure.mapper.draw.IAwardDao;
import com.starnft.star.infrastructure.mapper.draw.IStrategyDao;
import com.starnft.star.infrastructure.mapper.draw.IStrategyDetailDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityRepository implements IActivityRepository {

    private Logger logger = LoggerFactory.getLogger(ActivityRepository.class);
    @Resource
    private StarScheduleSeckillMapper starScheduleSeckillMapper;

    @Resource
    private IActivityDao activityDao;
    @Resource
    private IAwardDao awardDao;
    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public int subtractionActivityStock(Long activityId) {
        return activityDao.subtractionActivityStock(activityId);
    }

    @Override
    public List<DrawActivityVO> scanToDoActivityList(Long id) {
        List<Activity> activityList = activityDao.scanToDoActivityList(id);
        List<DrawActivityVO> activityVOList = new ArrayList<>(activityList.size());
        for (Activity activity : activityList) {
            DrawActivityVO activityVO = new DrawActivityVO();
            activityVO.setId(activity.getId());
            activityVO.setActivityId(activity.getActivityId());
            activityVO.setActivityName(activity.getActivityName());
            activityVO.setBeginDateTime(activity.getBeginDateTime());
            activityVO.setEndDateTime(activity.getEndDateTime());
            activityVO.setState(activity.getState());
            activityVOList.add(activityVO);
        }
        return activityVOList;
    }

    @Override
    public void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code) {
        if (null == tokenKey) {
            return;
        }
        // 删除分布式锁 Key
        redisUtil.del(tokenKey);
    }

    @Override
    public List<ActivityVO> obtainActivities(String startTime, String endTime, List<String> keys) {
        List<StarScheduleSeckill> starScheduleSeckills = starScheduleSeckillMapper.obtainActivities(startTime, endTime, keys);
        return BeanColverUtil.colverList(starScheduleSeckills, ActivityVO.class);
    }

    @Override
    public boolean modifyStock(Integer spuId, Integer stock) {
        StarScheduleSeckill starScheduleSeckill = starScheduleSeckillMapper.queryByThemeId(spuId);
        return starScheduleSeckillMapper.modifyStock(spuId, stock, starScheduleSeckill.getVersion()) == 1;
    }

    @Override
    public DrawActivityVO getDrawActivity(PartakeReq partakeReq) {
        Activity activity = activityDao.queryActivityById(partakeReq.getActivityId());

        if (activity == null) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "未找到该活动");
        }
        return buildDrawActivityVO(activity);
    }

    private DrawActivityVO buildDrawActivityVO(Activity activity) {
        DrawActivityVO activityVO = new DrawActivityVO();
        activityVO.setId(activity.getId());
        activityVO.setActivityId(activity.getActivityId());
        activityVO.setActivityName(activity.getActivityName());
        activityVO.setActivityDesc(activity.getActivityDesc());
        activityVO.setBeginDateTime(activity.getBeginDateTime());
        activityVO.setEndDateTime(activity.getEndDateTime());
        activityVO.setStockCount(activity.getStockCount());
        activityVO.setStockSurplusCount(activity.getStockSurplusCount());
        activityVO.setTakeCount(activity.getTakeCount());
        activityVO.setStrategyId(activity.getStrategyId());
        activityVO.setState(activity.getState());
        activityVO.setCreator(activity.getCreator());
        activityVO.setCreateTime(activity.getCreateTime());
        activityVO.setUpdateTime(activity.getUpdateTime());

        return activityVO;

    }
}
