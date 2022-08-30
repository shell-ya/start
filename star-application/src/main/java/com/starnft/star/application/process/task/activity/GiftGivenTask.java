package com.starnft.star.application.process.task.activity;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.starnft.star.application.process.task.context.GiftGivenParam;
import com.starnft.star.domain.activity.IActivitiesService;
import com.starnft.star.domain.activity.model.vo.GoodsHavingTimesVO;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.serivce.INumberService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GiftGivenTask {

    @Resource
    INumberService numberService;

    @Resource
    IActivitiesService activitiesService;

    @XxlJob("giftGivenTask")
    public void statisticsNGift() {
        String jsonContext = XxlJobHelper.getJobParam();
        GiftGivenParam giftGivenParam = JSON.parseObject(jsonContext, GiftGivenParam.class);

        //当前持有者信息
        List<NumberDetailVO> numbers =
                numberService.queryNumberNotOnSell(Long.parseLong(giftGivenParam.getThemeInfoId()));
        Map<String, NumberDetailVO> uidMapping = numbers.stream().collect(Collectors.toMap(NumberDetailVO::getOwnerBy, nu -> nu));


        //与历史持有信息对比
        List<GoodsHavingTimesVO> hisTimes = activitiesService.queryGoodsHavingTimesByGood(Long.parseLong(giftGivenParam.getThemeInfoId()));
        Map<Long, GoodsHavingTimesVO> hisMapping = hisTimes.stream().collect(Collectors.toMap(GoodsHavingTimesVO::getUid, nu -> nu));

        if (CollectionUtil.isEmpty(hisTimes)) {
            for (NumberDetailVO number : numbers) {
                activitiesService.initGoodsHavingTimes(number);
                deliveryScopes(number.getOwnerBy(), 1);
            }
            return;
        }

        //清除未持有的
        for (GoodsHavingTimesVO hisTime : hisTimes) {
            NumberDetailVO numberDetailVO = uidMapping.get(String.valueOf(hisTime.getUid()));
            //遍历历史数据 在当前持有信息中找不到 藏品被出售不被持有 ，清除持有信息数据 不发放积分
            if (numberDetailVO == null) {
//                 activitiesService.delTimes(hisTime.getUid(), giftGivenParam.getThemeInfoId());
            }
            // 增加持有天数 发放对应持有阶段积分

        }

        //新增 新购入的
        for (NumberDetailVO number : numbers) {
            GoodsHavingTimesVO goodsHavingTimesVO = hisMapping.get(Long.parseLong(number.getOwnerBy()));
            if (goodsHavingTimesVO == null){
                activitiesService.initGoodsHavingTimes(number);
                deliveryScopes(number.getOwnerBy(), 1);
            }
        }

    }

    private void deliveryScopes(String ownerBy, int i) {

    }
}
