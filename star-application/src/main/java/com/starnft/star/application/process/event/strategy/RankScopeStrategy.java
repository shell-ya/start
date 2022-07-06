package com.starnft.star.application.process.event.strategy;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.application.process.rank.strategy.IRankStrategy;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import com.starnft.star.domain.rank.core.rank.core.IRankService;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component("rankScopeStrategy")
@Slf4j
public class RankScopeStrategy implements EventStrategy {
    @Resource
    IRankService rankService;
    @Resource
    ApplicationContext context;
    @Override
    public StarConstants.ActivityType getEventType() {
        return StarConstants.ActivityType.Rank;
    }

    @Override
    public void handler(EventActivityExtRes ext, ActivityEventReq activityEventReq) {
        log.info("params参数为「{}」",ext.getParams());
        JSONObject configs = JSONUtil.parseObj(ext.getParams());
        String rankName = configs.getStr("rankName");
        if (Objects.isNull(rankName)) {
            log.error("排行版不存在,请先配置排行版");
            throw new StarException("排行版不存在,请先配置排行版");

        }
        RankDefinition rankDefinition = rankService.getRank(rankName);
        if (Objects.isNull(rankDefinition)) {

            log.error("{}排行版配置不存在,请先配置排行版", rankName);
            throw new StarException("排行版不存在,请先配置排行版");

        }
        if (rankDefinition.getIsTime().equals(StarConstants.EventStatus.EVENT_STATUS_OPEN)) {
            log.info("排行版开启时间限制");
            if (rankDefinition.getStartTime().after(new Date()) || rankDefinition.getEndTime().before(new Date())) {
                log.info("排行版时间未启动或则已结束");
                return;
            }
            //依据不同的排行版进行相关的处理
            Map<String, IRankStrategy> rankStrategyMap = context.getBeansOfType(IRankStrategy.class);
            for (IRankStrategy rankStrategy : rankStrategyMap.values()) {

                if (rankStrategy.getRankType().getValue().equals(rankDefinition.getRankType())){
                    log.info("找到关于「{}」的排行榜",rankStrategy.getRankType().getDesc());
                    rankStrategy.handler(rankDefinition,ext,activityEventReq);
                }
            }

        }

    }
}
