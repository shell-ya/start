package com.starnft.star.application.process.task.activity;

import com.google.common.collect.Lists;
import com.starnft.star.domain.series.model.vo.SeriesVO;
import com.starnft.star.domain.series.service.SeriesService;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.utils.DateUtil;
import com.starnft.star.domain.activity.IActivitiesService;
import com.starnft.star.domain.activity.model.vo.ActivityVO;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.service.ThemeService;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ActivitiesTask {

    private static Logger log = LoggerFactory.getLogger(ActivitiesTask.class);
    private static String strDateFormat = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
    //每个商品秒杀间隔
    private static final int interval = 2;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private IActivitiesService activitiesService;

    @Resource
    private ThemeService themeService;

    @Resource
    private SeriesService seriesService;

    /*****
     * 15秒执行一次
     * 0/15:表示从0秒开始执行，每过15秒再次执行
     *
     * 1、状态为审核通过
     * 2、库存为0
     * 3、秒杀开始时间>=当前循环的时间区间的开始时间
     * 4、秒杀结束时间<当前循环的时间区间的开始时间+2小时
     * 5、导入到Redis缓存。使用Hash类型存储
     */
    @Scheduled(cron = "0/15 * * * * ?")
    public void loadActivities() {
        //1.查询所有时间区间 2小时一个时区
        List<Date> dateMenus = DateUtil.getDateMenus(interval);

        for (Date startTime : dateMenus) {
            String startTimeTrim = DateUtil.date2Str(startTime);
            String goodsKey = String.format(RedisKey.SECKILL_GOODS_INFO.getKey(), startTimeTrim);
            Set<String> keys = redisUtil.hashKeys(goodsKey);
            //执行查询 加载时区内待秒杀商品
            List<SecKillGoods> secKillGoods = loadGoods(startTime, keys);
            //加载进缓存
            cacheGoods(secKillGoods, goodsKey);
        }

    }

    private void cacheGoods(List<SecKillGoods> secKillGoods, String goodsKey) {
        if (secKillGoods == null || secKillGoods.size() == 0) {
            return;
        }
        for (SecKillGoods secKillGood : secKillGoods) {
            boolean isSuccess = redisUtil.hset(goodsKey, String.valueOf(secKillGood.getThemeId()), secKillGood);
            if (isSuccess) {
                //[themeId,themeId,themeId] 将商品剩余库存放到Redis，解决并发超卖问题
                Long[] ids = pushIds(secKillGood.getStock(), secKillGood.getThemeId());
                String stockKey = String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), secKillGood.getThemeId());
                //创建库存队列
                redisUtil.addToListLeft(stockKey, RedisKey.SECKILL_GOODS_STOCK_QUEUE.getTime(), RedisKey.SECKILL_GOODS_STOCK_QUEUE.getTimeUnit(), (Object) ids);
                //设置库存量
                redisUtil.hashIncr(RedisKey.SECKILL_GOODS_STOCK_NUMBER.getKey(), String.valueOf(secKillGood.getThemeId()), secKillGood.getStock());

                log.info("当前时间: " + sdf.format(DateUtil.getDaDate()));
                log.info("加载商品 themeId:[{}] , seriesId:[{}] , themeName:[{}] , stock:[{}] time:[{}-{}]",
                        secKillGood.getThemeId(), secKillGood.getSeriesId(), secKillGood.getThemeName(), secKillGood.getStock(), secKillGood.getStartTime(), secKillGood.getEndTime());
                ;
            }
        }
    }

    private Long[] pushIds(Integer stock, Long themeId) {
        Long[] ids = new Long[stock];
        for (int i = 1; i <= stock; i++) {
            ids[i - 1] = (long) i;
        }
        return ids;
    }

    private List<SecKillGoods> loadGoods(Date startTime, Set<String> keys) {
        List<ActivityVO> activities = activitiesService.loadActivities(startTime, DateUtil.addDateHour(startTime, interval), new ArrayList<>(keys));
        if (activities == null || activities.size() == 0) return null;
        ArrayList<@Nullable SecKillGoods> goods = Lists.newArrayList();
        for (ActivityVO activity : activities) {
            ThemeDetailVO detailVO = themeService.queryThemeDetail(activity.getSpuId());
            SecKillGoods good = copy(activity, detailVO);
            goods.add(good);
        }
        return goods;
    }

    private SecKillGoods copy(ActivityVO activity, ThemeDetailVO detailVO) {

        SeriesVO seriesVO = seriesService.querySeriesById(detailVO.getSeriesId());
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
        return secKillGoods;
    }

}
