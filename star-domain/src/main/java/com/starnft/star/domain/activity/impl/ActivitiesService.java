package com.starnft.star.domain.activity.impl;

import com.starnft.star.common.utils.DateUtil;
import com.starnft.star.domain.activity.IActivitiesService;
import com.starnft.star.domain.activity.model.vo.ActivityVO;
import com.starnft.star.domain.activity.repository.IActivityRepository;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ActivitiesService implements IActivitiesService {

    @Resource
    private IActivityRepository activityRepository;

    @Override
    public List<ActivityVO> loadActivities(Date startTime, Date endTime, List<String> keys) {
        return activityRepository.obtainActivities(
                DateUtil.dateFormat(startTime, DateUtil.DATETIME_FORMATTER), DateUtil.dateFormat(endTime, DateUtil.DATETIME_FORMATTER), keys);
    }

    @Override
    public boolean modifyStock(Integer spuId, Integer stock) {

        return activityRepository.modifyStock(spuId,stock);
    }
}
