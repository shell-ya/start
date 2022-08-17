package com.starnft.star.application.process.draw.impl;

import com.starnft.star.application.process.draw.IActivityDrawProcess;
import com.starnft.star.application.process.draw.req.DrawProcessReq;
import com.starnft.star.application.process.draw.res.DrawProcessResult;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.activity.model.vo.DrawActivityVO;
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

        // 4. 发送MQ，触发发奖流程

        // 5. 返回结果
        return new DrawProcessResult("1", "");
    }


}
