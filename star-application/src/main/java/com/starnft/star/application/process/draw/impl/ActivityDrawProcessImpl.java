package com.starnft.star.application.process.draw.impl;

import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.process.draw.IActivityDrawProcess;
import com.starnft.star.application.process.draw.req.DrawProcessReq;
import com.starnft.star.application.process.draw.res.DrawProcessResult;
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
import com.starnft.star.domain.support.ids.IIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

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


    @Override
    public DrawProcessResult doDrawProcess(DrawProcessReq req) {
        // 1. 领取活动
        DrawActivityVO drawActivity = drawExec.getDrawActivity(new PartakeReq(req.getuId(), req.getActivityId()));
        // 2. 执行抽奖
        DrawResult drawResult = drawExec.doDrawExec(new DrawReq(req.getuId(), drawActivity.getStrategyId()));
        if (StarConstants.DrawState.FAIL.getCode().equals(drawResult.getDrawState())) {
            throw new RuntimeException();
        }
        DrawAwardVO drawAwardVO = drawResult.getDrawAwardInfo();

        // 3. 结果落库
        Result result = drawExec.recordDrawOrder(buildDrawOrderVO(req, drawActivity.getStrategyId(), idGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId(), drawAwardVO));
        if (!ResultCode.SUCCESS.getCode().equals(result.getCode())) {
            logger.error("抽奖结果落库失败 uid:[{}] activityId:[{}] award Info :[{}]", req.getuId(), req.getActivityId(), drawActivity);
            throw new RuntimeException("抽奖结果落库失败！");
        }

        // 4. 消耗抽奖物品

        // 5. 发送MQ，触发发奖流程
//        messageSender.send();
        // 6. 返回结果
        return new DrawProcessResult(StarError.SUCCESS_000000.getErrorCode(), "", drawAwardVO);
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
