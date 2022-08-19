package com.starnft.star.application.process.draw.impl;

import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.process.draw.IActivityDrawProcess;
import com.starnft.star.application.process.draw.req.DrawProcessReq;
import com.starnft.star.application.process.draw.res.DrawProcessResult;
import com.starnft.star.application.process.draw.vo.DrawConsumeVO;
import com.starnft.star.common.Result;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.domain.activity.model.vo.DrawActivityVO;
import com.starnft.star.domain.activity.model.vo.DrawOrderVO;
import com.starnft.star.domain.draw.model.req.DrawReq;
import com.starnft.star.domain.draw.model.req.PartakeReq;
import com.starnft.star.domain.draw.model.res.DrawResult;
import com.starnft.star.domain.draw.model.vo.DrawAwardVO;
import com.starnft.star.domain.draw.service.draw.IDrawExec;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.support.ids.IIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
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


    @Override
    public DrawProcessResult doDrawProcess(DrawProcessReq req) {

        // 0. 领取活动
        DrawActivityVO drawActivity = drawExec.getDrawActivity(new PartakeReq(req.getuId(), req.getActivityId()));
        drawActivity.setConsumeItemId(drawActivity.getActivityId());

        // 1. 挂售状态判断
        isOnSell(req, drawActivity);

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
        if (drawActivity.getCanSell() == 1) {
            Boolean isOnTSell = iNumberService.queryThirdPlatSell(Long.parseLong(req.getuId()), Long.parseLong(req.getNumberId()));
//            NumberDetailVO numberDetail = iNumberService.getNumberDetail(Long.parseLong(req.getNumberId()));
//            if (isOnTSell || numberDetail != null) {
//                throw new StarException(StarError.BLIND_IS_ON_SELL);
//            }
        }

    }

    private Boolean comsumeItem(DrawConsumeVO drawConsumeVO) {

        // TODO: 2022/8/18 策略设计 不同抽奖活动消耗不同标的
        return iNumberService.comsumeNumber(Long.parseLong(drawConsumeVO.getDrawAwardVO().getuId()), Long.parseLong(drawConsumeVO.getNumberId()));
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
