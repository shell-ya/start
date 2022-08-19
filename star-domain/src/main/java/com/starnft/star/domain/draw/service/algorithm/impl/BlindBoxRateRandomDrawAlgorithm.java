package com.starnft.star.domain.draw.service.algorithm.impl;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.draw.annotation.Strategy;
import com.starnft.star.domain.draw.model.vo.AwardRateVO;
import com.starnft.star.domain.draw.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Component("blindBoxRateRandomDrawAlgorithm")
@Strategy(strategyMode = StarConstants.StrategyMode.BLANKOFFSET)
public class BlindBoxRateRandomDrawAlgorithm extends BaseAlgorithm {

    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {
        List<AwardRateVO> awardRateVOS = awardRateInfoMap.get(strategyId);

        //获取概率最小值
        AwardRateVO awardRateVO = awardRateVOS.stream().min(Comparator.comparing(AwardRateVO::getAwardRate)).get();
        BigDecimal awardRate = awardRateVO.getAwardRate();

        // bounds 根据最小值获取 随机边界
        int rateStandards = getRateStandards(awardRate.doubleValue());

        //根据边界生成随机数
        SecureRandom secureRandom = new SecureRandom();
        int randomValue = secureRandom.nextInt(rateStandards);

        // 搜索随机数落在所对应区间
        List<AwardRateVO> rates = awardRateVOS.stream().sorted(Comparator.comparing(AwardRateVO::getAwardRate)).collect(Collectors.toList());
        int currBounds = 0;
        for (AwardRateVO rate : rates) {
            currBounds += rate.getAwardRate().multiply(new BigDecimal(rateStandards)).intValue();
            if (randomValue <= currBounds) {
                return rate.getAwardId();
            }
        }
        return null;
    }

    @Override
    public String boundMoving(Long strategyId, String awardId, BiFunction<Long, String, Boolean> deductStock) {

        //概率从小到大排序
        List<AwardRateVO> awardRateVOS = awardRateInfoMap.get(strategyId);
        List<AwardRateVO> sorted = awardRateVOS.stream().sorted(Comparator.comparing(AwardRateVO::getAwardRate)).collect(Collectors.toList());

        //找到当前无库存奖品索引
        int currAwardIndex = -1;
        for (int i = 0; i < sorted.size(); i++) {
            AwardRateVO awardRateVO = sorted.get(i);
            if (awardRateVO.getAwardId().equals(awardId)) {
                currAwardIndex = i;
            }
        }

        if (currAwardIndex != -1) {
            //以当前奖品索引开始向大概率奖品处偏移
            for (int i = currAwardIndex; i < sorted.size() - 1; i++) {
                if (deductStock.apply(strategyId, sorted.get(i).getAwardId())) {
                    return sorted.get(i).getAwardId();
                }
            }
            //大概率奖品均无库存 以当前奖品索引开始向小概率奖品处偏移
            for (int i = currAwardIndex; i >= 0; i--) {
                if (deductStock.apply(strategyId, sorted.get(i).getAwardId())) {
                    return sorted.get(i).getAwardId();
                }
            }
        }

        // 全部库存耗尽 抛出异常
        throw new StarException(StarError.BLIND_STOCK_IS_NULL);
    }


    private static int getRateStandards(double minRate) {
        int size = 1;
        while (minRate < 1.0d) {
            minRate = minRate * 10;
            size = size * 10;
        }
        return size;
    }
}
