package com.starnft.star.domain.activity.repository;

import com.starnft.star.domain.activity.model.vo.ActivityVO;

import java.util.List;

public interface IActivityRepository {

    List<ActivityVO> obtainActivities(String startTime, String endTime, List<String> keys);

    boolean modifyStock(Integer spuId, Integer stock);

}
