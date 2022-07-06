package com.starnft.star.application.process.rank.strategy.action;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RankBuyActionState implements  IRankActionState {
    @Override
    public StarConstants.EventSign getState() {
        return StarConstants.EventSign.Buy;
    }
    @Override
    public void manage(ActivityEventReq activityEventReq, RankDefinition rankDefinition) {
        log.info("进入拉新排行版购买动作");
    }
}
