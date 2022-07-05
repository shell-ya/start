package com.starnft.star.application.process.event.strategy;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import com.starnft.star.domain.rank.core.rank.core.IRankService;
import com.starnft.star.domain.rank.core.rank.model.RankItemMetaData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component("rankScopeStrategy")
@Slf4j
public class RankScopeStrategy implements EventStrategy
{
    @Resource
    IRankService rankService;

    @Override
    public StarConstants.ActivityType getEventType() {
        return StarConstants.ActivityType.Rank;
    }
    @Override
    public void handler(EventActivityExtRes ext, ActivityEventReq activityEventReq) {
        JSONObject configs = JSONUtil.parseObj(ext.getParams());
        String rankName = configs.getStr("rankName");
        if (Objects.isNull(rankName)) {
            log.info("排行版不存在,请先配置排行版");
        }
        RankItemMetaData rank = rankService.getRank(rankName);
        if (Objects.isNull(rank)) {
            log.info("{}排行版配置不存在,请先配置排行版",rankName);
        }
        //todo 依据不同排行版类型执行不同的排行版参数录入
//        Map<String, Object> params = activityEventReq.getParams();
//        Long userId = activityEventReq.getUserId();
//        Long parent = (Long) params.get("parent");
//
//
//        rankService.put(rankName,)
    }
}
