package com.starnft.star.domain.activity.impl;

import com.starnft.star.common.utils.DateUtil;
import com.starnft.star.domain.activity.IActivitiesService;
import com.starnft.star.domain.activity.model.vo.ActivityVO;
import com.starnft.star.domain.activity.model.vo.GoodsHavingTimesVO;
import com.starnft.star.domain.activity.repository.IActivityRepository;
import com.starnft.star.domain.draw.model.vo.DrawAwardExportVO;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
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

        return activityRepository.modifyStock(spuId, stock);
    }

    @Override
    public boolean delErrorExport(String orderId) {
        return activityRepository.deleteExport(orderId);
    }

    @Override
    public void delTimes(Long uid, Long themeId) {
        activityRepository.delTimes(uid, themeId);
    }

    @Override
    public Integer addTimes(Long uid, Long themeId, Integer version) {
        return activityRepository.addTimes(uid, themeId, version);
    }

    public List<DrawAwardExportVO> queryUserExportList(long id) {
        return activityRepository.queryUserExportList(id);
    }

    @Override
    public List<GoodsHavingTimesVO> queryGoodsHavingTimesByGood(Long themeId) {
        return activityRepository.queryGoodsHavingTimesByGood(themeId);
    }

    @Override
    public void initGoodsHavingTimes(NumberDetailVO numberDetailVO) {
        activityRepository.initGoodsHavingTimes(numberDetailVO);
    }
}
