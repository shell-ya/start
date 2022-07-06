package com.starnft.star.application.process.rank.strategy.action;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.application.process.event.model.BuyActivityEventReq;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class RankBuyActionState implements  IRankActionState {
    @Resource
    IUserRepository iUserRepository;
    @Override
    public StarConstants.EventSign getState() {
        return StarConstants.EventSign.Buy;
    }
    @Override
    public void manage(ActivityEventReq activityEventReq, RankDefinition rankDefinition, EventActivityExtRes extArrays) {

        log.info("进入拉新排行版购买动作");
        Map<String, Object> params = activityEventReq.getParams();
        BuyActivityEventReq buyActivityEventReq = BeanUtil.mapToBean(params, BuyActivityEventReq.class, true);
        log.info("进入参数为「{}」", JSONUtil.toJsonStr(buyActivityEventReq));
        UserInfo userInfo = iUserRepository.queryUserInfoByUserId(buyActivityEventReq.getUserId());
        if (Objects.isNull(userInfo)){
            log.info("用户「{}」不存在",activityEventReq.getUserId());
            return;
        }
        if (Objects.isNull(userInfo.getParent())){
            log.info("用户「{}」上级不存在不存在",activityEventReq.getUserId());
            return;
        }
        String extConfigs = extArrays.getParams();
        if (Objects.isNull(extConfigs)){
            log.info("ext 配置为空");
        }
        JSONObject extConfigsParams = JSONUtil.parseObj(extConfigs);
        Boolean isCheckThemeId = extConfigsParams.getBool("isCheckThemeId", false);
        if (isCheckThemeId){
            Long themeId = extConfigsParams.getLong("themeId");
            if (!buyActivityEventReq.getThemeId().equals(themeId)){
                log.info("检测主题不一致");
                return;
            }
        }

    }
}
