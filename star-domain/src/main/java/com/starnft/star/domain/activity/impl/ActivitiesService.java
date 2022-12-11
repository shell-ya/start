package com.starnft.star.domain.activity.impl;

import com.starnft.star.common.utils.DateUtil;
import com.starnft.star.domain.activity.IActivitiesService;
import com.starnft.star.domain.activity.model.vo.ActivityVO;
import com.starnft.star.domain.activity.model.vo.DrawBuffTimesRes;
import com.starnft.star.domain.activity.model.vo.GoodsHavingTimesVO;
import com.starnft.star.domain.activity.model.vo.LuckyGuysVO;
import com.starnft.star.domain.activity.repository.IActivityRepository;
import com.starnft.star.domain.draw.model.vo.DrawAwardExportVO;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.publisher.model.req.PublisherReq;
import com.starnft.star.domain.publisher.model.vo.PublisherVO;
import com.starnft.star.domain.publisher.service.PublisherService;
import com.starnft.star.domain.series.model.vo.SeriesVO;
import com.starnft.star.domain.series.service.SeriesService;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.service.ThemeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivitiesService implements IActivitiesService {

    @Resource
    private IActivityRepository activityRepository;

    @Resource
    private ThemeService themeService;

    @Resource
    private SeriesService seriesService;

    @Resource
    private PublisherService publisherService;

    @Override
    public SecKillGoods getActivityByThemeId(Long themeId) {
        ActivityVO activity = activityRepository.getActivityByThemeId(themeId);
        ThemeDetailVO detailVO = themeService.queryThemeDetail(activity.getSpuId());
        return copy(activity, detailVO);
    }


    private SecKillGoods copy(ActivityVO activity, ThemeDetailVO detailVO) {

        PublisherVO publisherVO = publisherService.queryPublisher(new PublisherReq(detailVO.getPublisherId()));
        if (publisherVO == null) {
            log.error("themeInfo: [{}] publisherId : [{}]", detailVO, detailVO.getPublisherId());
            throw new RuntimeException("未找到对应发行商");
        }
        SeriesVO seriesVO = seriesService.querySeriesById(detailVO.getSeriesId());
        if (publisherVO == null) {
            log.error("themeInfo: [{}] seriesId : [{}]", detailVO, detailVO.getSeriesId());
            throw new RuntimeException("未找到对应系列");
        }
        SecKillGoods secKillGoods = new SecKillGoods();
        secKillGoods.setGoodsNum(activity.getGoodsNum());
        secKillGoods.setDescrption(detailVO.getDescrption());
        secKillGoods.setSecCost(activity.getSecCost());
        secKillGoods.setEndTime(activity.getEndTime());
        secKillGoods.setStock(activity.getStock());
        secKillGoods.setStartTime(activity.getStartTime());
        secKillGoods.setThemeId(detailVO.getId());
        secKillGoods.setSeriesId(detailVO.getSeriesId());
        secKillGoods.setSeriesName(seriesVO.getSeriesName());
        secKillGoods.setThemeLevel(detailVO.getThemeLevel());
        secKillGoods.setThemeName(detailVO.getThemeName());
        secKillGoods.setThemePic(detailVO.getThemePic());
        secKillGoods.setThemeType(detailVO.getThemeType());
        secKillGoods.setPublisherId(publisherVO.getPublisherId());
        secKillGoods.setPublisherPic(publisherVO.getPublisherPic());
        secKillGoods.setPublisherName(publisherVO.getPublisherName());
        secKillGoods.setVersion(activity.getVersion());
        return secKillGoods;
    }

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
    public boolean frozeStock(Integer spuId, Integer stock, Integer version) {
        return activityRepository.frozenStock(spuId, stock,version);
    }

    @Override
    public boolean delErrorExport(String uId, String orderId) {
        return activityRepository.deleteExport(uId, orderId);
    }

    @Override
    public void delTimes(Long uid, Long themeId) {
        activityRepository.delTimes(uid, themeId);
    }

    @Override
    public Integer addTimes(Long uid, Long themeId, Integer version) {
        return activityRepository.addTimes(uid, themeId, version);
    }

    @Override
    public DrawBuffTimesRes queryBuffTimes(String uid, String awardId) {
        return activityRepository.queryBuffTimes(uid, awardId);
    }

    @Override
    public List<LuckyGuysVO> luckyGuys() {
        return activityRepository.luckyGuys(10000003L).stream().sorted(Comparator.comparing(LuckyGuysVO::getLuckyTime).reversed()).collect(Collectors.toList());
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
