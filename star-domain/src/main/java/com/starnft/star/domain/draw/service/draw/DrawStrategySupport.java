package com.starnft.star.domain.draw.service.draw;

import com.starnft.star.common.Result;
import com.starnft.star.domain.activity.model.vo.DrawActivityVO;
import com.starnft.star.domain.activity.model.vo.DrawOrderVO;
import com.starnft.star.domain.activity.repository.IActivityRepository;
import com.starnft.star.domain.draw.model.aggregates.StrategyRich;
import com.starnft.star.domain.draw.model.req.PartakeReq;
import com.starnft.star.domain.draw.model.vo.AwardBriefVO;
import com.starnft.star.domain.draw.repository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @description: 抽奖策略数据支撑，一些通用的数据服务
 */
@Slf4j
public class DrawStrategySupport extends DrawConfig {

    @Resource
    protected IStrategyRepository strategyRepository;


    @Resource
    protected IActivityRepository activityRepository;

    @Resource
    private TransactionTemplate transactionTemplate;


    DrawActivityVO getDrawActivity(PartakeReq partakeReq) {
        return activityRepository.getDrawActivity(partakeReq);
    }

    /**
     * 查询策略配置信息
     *
     * @param strategyId 策略ID
     * @return 策略配置信息
     */
    protected StrategyRich queryStrategyRich(Long strategyId) {
        return strategyRepository.queryStrategyRich(strategyId);
    }

    /**
     * 查询奖品详情信息
     *
     * @param awardId 奖品ID
     * @return 中奖详情
     */
    protected AwardBriefVO queryAwardInfoByAwardId(String awardId) {
        return strategyRepository.queryAwardInfo(awardId);
    }

    public Result recordDrawOrder(DrawOrderVO drawOrder) {
        activityRepository.saveUserStrategyExport(drawOrder);
        return transactionTemplate.execute(status -> {
            try {
                activityRepository.saveUserStrategyExport(drawOrder);
            } catch (Exception e) {
                status.setRollbackOnly();
                log.error("记录中奖单，唯一索引冲突 activityId：{} uId：{}", drawOrder.getActivityId(), drawOrder.getuId(), e);
                return Result.buildResult(2000001, "唯一索引冲突");
            }
            return Result.buildSuccessResult();
        });
    }

}
