package com.starnft.star.application.process.rank.strategy.action;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.application.process.event.model.BuyActivityEventReq;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

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
        Map<String, Object> params = activityEventReq.getParams();
        BuyActivityEventReq buyActivityEventReq = BeanUtil.mapToBean(params, BuyActivityEventReq.class, true);
       log.info("进入参数为「{}」", JSONUtil.toJsonStr(buyActivityEventReq));

    }
}
