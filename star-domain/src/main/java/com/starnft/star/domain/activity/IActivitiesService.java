package com.starnft.star.domain.activity;

import com.alicp.jetcache.anno.Cached;
import com.starnft.star.domain.activity.model.vo.ActivityVO;
import com.starnft.star.domain.activity.model.vo.DrawBuffTimesRes;
import com.starnft.star.domain.activity.model.vo.GoodsHavingTimesVO;
import com.starnft.star.domain.activity.model.vo.LuckyGuysVO;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;

import java.util.Date;
import java.util.List;

public interface IActivitiesService {

    List<ActivityVO> loadActivities(Date startTime, Date endTime, List<String> keys);

    boolean modifyStock(Integer spuId, Integer stock);

    /**
     * 冻结库存
     * @param spuId
     * @param stock
     * @return
     */
    boolean frozeStock(Integer spuId, Integer stock, Integer version);

    List<GoodsHavingTimesVO> queryGoodsHavingTimesByGood(Long themeId);

    void initGoodsHavingTimes(NumberDetailVO numberDetailVO);

    boolean delErrorExport(String uId,String orderId);

    void delTimes(Long uid, Long themeId);

    Integer addTimes(Long uid, Long themeId, Integer version);

    DrawBuffTimesRes queryBuffTimes(String uid , String awardId);

    @Cached(name="IActivitiesService.luckyGuys", expire = 300)
    List<LuckyGuysVO> luckyGuys();

    SecKillGoods getActivityByThemeId(Long themeId);

}
