package com.starnft.star.domain.activity;

import com.starnft.star.domain.activity.model.vo.ActivityVO;
import com.starnft.star.domain.activity.model.vo.GoodsHavingTimesVO;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;

import java.util.Date;
import java.util.List;

public interface IActivitiesService {

    List<ActivityVO> loadActivities(Date startTime, Date endTime, List<String> keys);

    boolean modifyStock(Integer spuId, Integer stock);

    List<GoodsHavingTimesVO> queryGoodsHavingTimesByGood(Long themeId);

    void initGoodsHavingTimes(NumberDetailVO numberDetailVO);

    boolean delErrorExport(String uId,String orderId);

    void delTimes(Long uid, Long themeId);

    Integer addTimes(Long uid, Long themeId, Integer version);
}
