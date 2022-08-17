package com.starnft.star.domain.activity.repository;

import com.starnft.star.domain.activity.model.vo.ActivityVO;
import com.starnft.star.domain.activity.model.vo.DrawActivityVO;
import com.starnft.star.domain.draw.model.req.PartakeReq;

import java.util.List;

public interface IActivityRepository {

    List<ActivityVO> obtainActivities(String startTime, String endTime, List<String> keys);

    boolean modifyStock(Integer spuId, Integer stock);



    DrawActivityVO getDrawActivity(PartakeReq partakeReq);

    /**
     * 扣减活动库存
     *
     * @param activityId 活动ID
     * @return 扣减结果
     */
    int subtractionActivityStock(Long activityId);

    /**
     * 扫描待处理的活动列表，状态为：通过、活动中
     *
     * @param id ID
     * @return 待处理的活动集合
     */
    List<DrawActivityVO> scanToDoActivityList(Long id);

    /**
     * 恢复活动库存，通过Redis 【如果非常异常，则需要进行缓存库存恢复，只保证不超卖的特性，所以不保证一定能恢复占用库存，另外最终可以由任务进行补偿库存】
     *
     * @param activityId    活动ID
     * @param tokenKey      分布式 KEY 用于清理
     * @param code          状态
     */
    void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code);

}
