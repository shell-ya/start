package com.starnft.star.application.process.draw.impl;

import cn.hutool.core.lang.Assert;
import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.process.draw.IActivityDrawProcess;
import com.starnft.star.application.process.draw.req.DrawProcessReq;
import com.starnft.star.application.process.draw.res.DrawProcessResult;
import com.starnft.star.application.process.draw.strategy.DrawConsumeExecutor;
import com.starnft.star.application.process.draw.vo.DrawConsumeVO;
import com.starnft.star.common.Result;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.enums.ActivitiesConsumeEnum;
import com.starnft.star.common.enums.NumberStatusEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.SpringUtil;
import com.starnft.star.domain.activity.model.vo.DrawActivityVO;
import com.starnft.star.domain.activity.model.vo.DrawOrderVO;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.draw.model.req.DrawReq;
import com.starnft.star.domain.draw.model.req.PartakeReq;
import com.starnft.star.domain.draw.model.res.DrawResult;
import com.starnft.star.domain.draw.model.vo.DrawAwardVO;
import com.starnft.star.domain.draw.service.draw.IDrawExec;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.support.ids.IIdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


/**
 * @description: 活动抽奖流程编排
 */
@Service
public class ActivityDrawProcessImpl implements IActivityDrawProcess {

    private Logger logger = LoggerFactory.getLogger(ActivityDrawProcessImpl.class);

    @Resource
    private IDrawExec drawExec;

    @Resource
    private Map<StarConstants.Ids, IIdGenerator> idGeneratorMap;

    @Resource
    private IMessageSender messageSender;

    @Resource
    private INumberService iNumberService;

    @Resource
    private RedisLockUtils redisLockUtils;


    @Override
    public DrawProcessResult doDrawProcess(DrawProcessReq req) {
        String lockKey = "";
        boolean isBlindbox = !StringUtils.isBlank(req.getNumberId());
        if (isBlindbox) {
            lockKey = String.format(RedisKey.DRAW_AWARD_OPEN_LOCK.getKey(), req.getNumberId());
            Boolean lock = redisLockUtils.lock(lockKey, RedisKey.DRAW_AWARD_OPEN_LOCK.getTime());
            Assert.isTrue(lock, () -> new StarException(StarError.SYSTEM_ERROR, "请勿重复开启"));
        }
        try {
            // 0. 领取活动
            DrawActivityVO drawActivity = drawExec.getDrawActivity(new PartakeReq(req.getuId(), req.getActivityId()));
            drawActivity.setConsumeItemId(drawActivity.getActivityId());

            if (new Date().before(drawActivity.getBeginDateTime()) || new Date().after(drawActivity.getEndDateTime())) {
                throw new StarException(StarError.INVALID_DRAW_REQUEST, "未在活动开启时间内！");
            }

            // 1. 挂售状态判断 当前物品持有性判断
            if (isBlindbox) isOnSell(req, drawActivity);

            // 2. 执行抽奖
            DrawResult drawResult = drawExec.doDrawExec(new DrawReq(req.getuId(), drawActivity.getStrategyId()));
            if (StarConstants.DrawState.FAIL.getCode().equals(drawResult.getDrawState())) {
                throw new RuntimeException();
            }
            DrawAwardVO drawAwardVO = drawResult.getDrawAwardInfo();

            // 3. 结果落库
            DrawOrderVO drawOrderVO = buildDrawOrderVO(req, drawActivity.getStrategyId(), idGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId(), drawAwardVO);
            Result result = drawExec.recordDrawOrder(drawOrderVO);
            if (!ResultCode.SUCCESS.getCode().equals(result.getCode())) {
                logger.error("抽奖结果落库失败 uid:[{}] activityId:[{}] award Info :[{}]", req.getuId(), req.getActivityId(), drawActivity);
                throw new RuntimeException("抽奖结果落库失败！");
            }

            DrawConsumeVO drawConsumeVO = new DrawConsumeVO(req.getNumberId(), drawAwardVO, drawActivity, drawOrderVO);
            // 4. 消耗抽奖物品
            Boolean isSuccess = comsumeItem(drawConsumeVO);
            // 5. 发送MQ，触发发奖流程
            if (isSuccess) {
                sendMessage(req, drawOrderVO, drawConsumeVO);
            }
            // 6. 返回结果
            return new DrawProcessResult(StarError.SUCCESS_000000.getErrorCode(), StarError.SUCCESS_000000.getErrorMessage(), drawAwardVO);
        } catch (RuntimeException e) {
            redisLockUtils.unlock(lockKey);
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(DrawProcessReq req, DrawOrderVO drawOrderVO, DrawConsumeVO drawConsumeVO) {

        String topic = String.format(TopicConstants.AWARD_DELIVERY.getFormat()
                , TopicConstants.AWARD_DELIVERY.getTag());

        messageSender.asyncSend(topic, Optional.of(drawConsumeVO),
                sendResult -> {
                    drawExec.updateInvoiceMqState(req.getuId(), drawOrderVO.getOrderId(), StarConstants.MQState.COMPLETE.getCode());
                },
                () -> {
                    drawExec.updateInvoiceMqState(req.getuId(), drawOrderVO.getOrderId(), StarConstants.MQState.FAIL.getCode());
                });
    }

    private void isOnSell(DrawProcessReq req, DrawActivityVO drawActivity) {

        Boolean owner = iNumberService.isOwner(Long.parseLong(req.getuId()), drawActivity.getConsumeItemId(), Long.parseLong(req.getNumberId()));
        if (!owner) {
            throw new StarException(StarError.INVALID_DRAW_REQUEST);
        }
        if (drawActivity.getCanSell() == 1) {
            Boolean isOnTSell = iNumberService.queryThirdPlatSell(Long.parseLong(req.getuId()), Long.parseLong(req.getNumberId()));
            Integer onSell = iNumberService.isOnSell(Long.parseLong(req.getuId()), drawActivity.getConsumeItemId(), Long.parseLong(req.getNumberId()));
            if (isOnTSell || Objects.equals(onSell, NumberStatusEnum.ON_CONSIGNMENT.getCode())) {
                throw new StarException(StarError.BLIND_IS_ON_SELL);
            }
        }

    }

    private Boolean comsumeItem(DrawConsumeVO drawConsumeVO) {

        for (ActivitiesConsumeEnum value : ActivitiesConsumeEnum.values()) {
            if (value.getCode().equals(drawConsumeVO.getDrawActivityVO().getConsumables())) {
                DrawConsumeExecutor bean = (DrawConsumeExecutor) SpringUtil.getBean(value.getBeanName());
                assert bean != null;
                return bean.doConsume(drawConsumeVO);
            }
        }
        throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "未找到消耗标的！");
    }

    private DrawOrderVO buildDrawOrderVO(DrawProcessReq req, Long strategyId, Long takeId, DrawAwardVO drawAwardVO) {
        long orderId = idGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId();
        DrawOrderVO drawOrderVO = new DrawOrderVO();
        drawOrderVO.setuId(req.getuId());
        drawOrderVO.setTakeId(takeId);
        drawOrderVO.setActivityId(req.getActivityId());
        drawOrderVO.setOrderId(orderId);
        drawOrderVO.setStrategyId(strategyId);
        drawOrderVO.setStrategyMode(drawAwardVO.getStrategyMode());
        drawOrderVO.setGrantType(drawAwardVO.getGrantType());
        drawOrderVO.setGrantDate(drawAwardVO.getGrantDate());
        drawOrderVO.setGrantState(StarConstants.GrantState.INIT.getCode());
        drawOrderVO.setAwardId(drawAwardVO.getAwardId());
        drawOrderVO.setAwardType(drawAwardVO.getAwardType());
        drawOrderVO.setAwardName(drawAwardVO.getAwardName());
        drawOrderVO.setAwardContent(drawAwardVO.getAwardContent());
        return drawOrderVO;
    }


}
