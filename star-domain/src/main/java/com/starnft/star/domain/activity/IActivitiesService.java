package com.starnft.star.domain.activity;

import com.starnft.star.domain.activity.model.vo.ActivityVO;

import java.util.Date;
import java.util.List;

public interface IActivitiesService {

    List<ActivityVO> loadActivities(Date startTime, Date endTime, List<String> keys);
}
