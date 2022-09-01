package com.starnft.star.application.process.task.activity;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.ScoreDTO;
import com.starnft.star.application.process.task.context.GiftGivenParam;
import com.starnft.star.common.enums.OwnAwardMapping;
import com.starnft.star.domain.activity.IActivitiesService;
import com.starnft.star.domain.activity.model.vo.GoodsHavingTimesVO;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.serivce.INumberService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GiftGivenTask {

    @Resource
    INumberService numberService;

    @Resource
    IActivitiesService activitiesService;

    @Resource
    IScopeCore scopeCore;

    @XxlJob("giftGivenTask")
    public void statisticsNGift() {
        String jsonContext = XxlJobHelper.getJobParam();
        GiftGivenParam giftGivenParam = JSON.parseObject(jsonContext, GiftGivenParam.class);

        //当前持有者信息
        List<NumberDetailVO> numbers =
                numberService.queryNumberNotOnSell(Long.parseLong(giftGivenParam.getThemeInfoId()));
        Map<String, List<NumberDetailVO>> uidMapping = numbers.stream().distinct().collect(Collectors.groupingBy(NumberDetailVO::getOwnerBy));

        //与历史持有信息对比
        List<GoodsHavingTimesVO> hisTimes = activitiesService.queryGoodsHavingTimesByGood(Long.parseLong(giftGivenParam.getThemeInfoId()));
        Map<Long, GoodsHavingTimesVO> hisMapping = hisTimes.stream().collect(Collectors.toMap(GoodsHavingTimesVO::getUid, nu -> nu));

        if (CollectionUtil.isEmpty(hisTimes)) {
            for (Map.Entry<String, List<NumberDetailVO>> number : uidMapping.entrySet()) {
                activitiesService.initGoodsHavingTimes(number.getValue().get(0));
                deliveryScopes(number.getValue().get(0).getOwnerBy(), number.getValue().get(0).getThemeId(), 1);
            }
            return;
        }

        //清除未持有的
        for (GoodsHavingTimesVO hisTime : hisTimes) {
            List<NumberDetailVO> numberDetailVO = uidMapping.get(String.valueOf(hisTime.getUid()));
            //遍历历史数据 在当前持有信息中找不到 藏品被出售不被持有 ，清除持有信息数据 不发放积分
            if (numberDetailVO == null || CollectionUtil.isEmpty(numberDetailVO)) {
                activitiesService.delTimes(hisTime.getUid(), Long.parseLong(giftGivenParam.getThemeInfoId()));
            }
            // 增加持有天数 发放对应持有阶段积分
            Integer isSuccess = activitiesService.addTimes(hisTime.getUid(), Long.parseLong(giftGivenParam.getThemeInfoId()), hisTime.getVersion());
            if (isSuccess == 1) {
                deliveryScopes(String.valueOf(hisTime.getUid()), hisTime.getThemeInfoId(), hisTime.getCountTimes() + 1);
            } else {
                log.error("uid :[{}] themeId:[{}] 增加持有天数失败！", hisTime.getUid(), Long.parseLong(giftGivenParam.getThemeInfoId()));
            }
        }

        //新增 新购入的
        for (Map.Entry<String, List<NumberDetailVO>> number : uidMapping.entrySet()) {
            GoodsHavingTimesVO goodsHavingTimesVO = hisMapping.get(Long.parseLong(number.getValue().get(0).getOwnerBy()));
            if (goodsHavingTimesVO == null) {
                activitiesService.initGoodsHavingTimes(number.getValue().get(0));
                deliveryScopes(number.getValue().get(0).getOwnerBy(), number.getValue().get(0).getThemeId(), 1);
            }
        }

    }

    private void deliveryScopes(String ownerBy, Long themeId, int i) {
        Long value = awardValueObtain(themeId, i);
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setUserId(Long.parseLong(ownerBy));
        scoreDTO.setScope(new BigDecimal(value));
        scoreDTO.setScopeType(0);
        String format = String.format("累积持有活动藏品%s天,", i);
        scoreDTO.setTemplate(format + "获得%s元石");
        scopeCore.userScopeManageAdd(scoreDTO);
    }

    private Long awardValueObtain(Long themeId, int i) {
        OwnAwardMapping max = null;
        for (OwnAwardMapping value : OwnAwardMapping.values()) {
            if ((max == null && value.getUniqueMark().equals(themeId)) ||
                    (value.getUniqueMark().equals(themeId) && value.getDayOf() > max.getDayOf())) max = value;
            if (value.getUniqueMark().equals(themeId) && value.getDayOf() == i) {
                return value.getAwardValue();
            }
        }
        assert max != null;
        return max.getAwardValue();
    }


}
