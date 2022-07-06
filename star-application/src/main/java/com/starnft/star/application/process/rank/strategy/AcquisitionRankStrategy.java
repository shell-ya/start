package com.starnft.star.application.process.rank.strategy;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.application.process.rank.strategy.action.IRankActionState;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AcquisitionRankStrategy implements IRankStrategy {

    @Resource
    ApplicationContext applicationContext;
    @Override
    public StarConstants.RankTypes getRankType() {
        return StarConstants.RankTypes.Acquisition;
    }
    @Override
    public void handler(RankDefinition rankDefinition, EventActivityExtRes extArrays, ActivityEventReq activityEventReq) {
        Map<String, IRankActionState> beans = applicationContext.getBeansOfType(IRankActionState.class);
        Map<StarConstants.EventSign, IRankActionState> rankActionStateArrays = beans.values().stream().collect(Collectors.toMap(IRankActionState::getState, Function.identity()));
        IRankActionState iRankActionState = rankActionStateArrays.get(StarConstants.EventSign.getEventSign(activityEventReq.getEventSign()));
        if (Objects.isNull(iRankActionState)){
            log.error("找不到关于动作「{}」的拉新动作策略器",activityEventReq.getEventSign());
            return;
        }
      iRankActionState.manage(activityEventReq,rankDefinition,extArrays);
}}
