package com.starnft.star.application.process.compose.strategy.lottery;

import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component("composeDrawScaleLotteryStrategy")
public class ComposeDrawScaleLotteryStrategy implements ComposeDrawLotteryStrategy {
    @Override
    public ComposePrizeDTO drawPrize(List<ComposePrizeDTO> composePrizes) {

        //验证奖品概率配置和为1
        BigDecimal rateTotal = BigDecimal.ZERO;
        //获取概率最小值以获取概率区间初始化
        BigDecimal minRate = BigDecimal.ZERO;
        for (ComposePrizeDTO composePrize : composePrizes) {
            rateTotal = rateTotal.add(composePrize.getPrizeProbability());
            if (rateTotal.compareTo(composePrize.getPrizeProbability()) <= 0) {
                minRate = composePrize.getPrizeProbability();
            }
        }
        if (rateTotal.intValue() != 1) {
            throw new RuntimeException("奖品概率配置错误！");
        }

        //根据最小概率确定 概率精度
        double minRateValue = minRate.doubleValue();
        if (minRateValue > 1.0 || minRateValue < (double) 0) {
            throw new RuntimeException("概率值有误!");
        }

        //在随机区间 生成随机数
        SecureRandom secureRandom = new SecureRandom();
        int rateStandards = getRateStandards(minRateValue);
        int shot = secureRandom.nextInt(rateStandards) + 1;
        System.out.println("当前生成的值为：" + shot);

        //将概率 从小到大排序
        List<ComposePrizeDTO> sortedComposePrize = composePrizes.stream()
                .sorted(Comparator.comparing(ComposePrizeDTO::getPrizeProbability)).collect(Collectors.toList());
        //遍历商品概率 累加 在范围内则返回对应商品
        BigDecimal currRate = BigDecimal.ZERO;
        for (ComposePrizeDTO prize : sortedComposePrize) {
            currRate = currRate.add(prize.getPrizeProbability());
            if (currRate.multiply(new BigDecimal(rateStandards)).intValue() >= shot) {
                return prize;
            }
        }
        //逻辑上没有该种可能可抛异常
       throw  new StarException("不可控异常");
    }


    private static int getRateStandards(double minRate) {
        int size = 10;
        while (minRate < 1.0d) {
            minRate = minRate * 10;
            size = size * 10;
        }
        return size;
    }


}
