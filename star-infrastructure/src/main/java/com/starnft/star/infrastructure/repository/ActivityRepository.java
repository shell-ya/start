package com.starnft.star.infrastructure.repository;

import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.activity.model.vo.ActivityVO;
import com.starnft.star.domain.activity.repository.IActivityRepository;
import com.starnft.star.infrastructure.entity.activity.StarScheduleSeckill;
import com.starnft.star.infrastructure.mapper.activity.StarScheduleSeckillMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private StarScheduleSeckillMapper starScheduleSeckillMapper;

    @Override
    public List<ActivityVO> obtainActivities(String startTime, String endTime, List<String> keys) {
        List<StarScheduleSeckill> starScheduleSeckills = starScheduleSeckillMapper.obtainActivities(startTime, endTime,keys);
        return BeanColverUtil.colverList(starScheduleSeckills, ActivityVO.class);
    }
}
