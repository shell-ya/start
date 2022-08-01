package com.starnft.star.application.process.compose.strategy.lottery;

import com.google.common.collect.Lists;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

//    public static void main(String[] args) {
////        List<@Nullable ComposePrizeDTO> prizeDTOS = Lists.newArrayList();
////        double all = 0;
////        for (int i = 0; i < 5; i++) {
////            ComposePrizeDTO composePrizeDTO = new ComposePrizeDTO();
////            SecureRandom secureRandom = new SecureRandom();
////            double rate = secureRandom.nextDouble() * (1.0 - 0.001) + 0.01;
////            all = all + rate;
////            if (all > 1.0) {
////                double newRate = 1.0 - all + rate;
////                composePrizeDTO.setPrizeProbability(BigDecimal.valueOf(newRate).setScale(4, BigDecimal.ROUND_HALF_DOWN));
////                prizeDTOS.add(composePrizeDTO);
////                break;
////            }
////            composePrizeDTO.setPrizeProbability(BigDecimal.valueOf(rate).setScale(4, BigDecimal.ROUND_HALF_DOWN));
////            prizeDTOS.add(composePrizeDTO);
////        }
////        BigDecimal zero = BigDecimal.ZERO;
////        for (ComposePrizeDTO prizeDTO : prizeDTOS) {
////            zero = zero.add(prizeDTO.getPrizeProbability());
////            System.out.println(prizeDTO + ": " + zero + "\n");
////        }
//
//        List<@Nullable ComposePrizeDTO> prizeDTOS = Lists.newArrayList();
//
//        prizeDTOS.add(ComposePrizeDTO.builder().prizeProbability(new BigDecimal(0.10).setScale(2, BigDecimal.ROUND_HALF_DOWN)).build());
//        prizeDTOS.add(ComposePrizeDTO.builder().prizeProbability(new BigDecimal(0.12).setScale(2, BigDecimal.ROUND_HALF_DOWN)).build());
//        prizeDTOS.add(ComposePrizeDTO.builder().prizeProbability(new BigDecimal(0.20).setScale(2, BigDecimal.ROUND_HALF_DOWN)).build());
//        prizeDTOS.add(ComposePrizeDTO.builder().prizeProbability(new BigDecimal(0.30).setScale(2, BigDecimal.ROUND_HALF_DOWN)).build());
//        prizeDTOS.add(ComposePrizeDTO.builder().prizeProbability(new BigDecimal(0.38).setScale(2, BigDecimal.ROUND_HALF_DOWN)).build());
//
//        Optional<@Nullable ComposePrizeDTO> min = prizeDTOS.stream().min(Comparator.comparing(composePrizeDTO -> composePrizeDTO != null ? composePrizeDTO.getPrizeProbability() : null));
//        Double minRate = min.get().getPrizeProbability().doubleValue();
//
//        double currRate = 0;
//        int times = 0;
//        int allTimes = 0;
//        while (currRate < (minRate - 0.05) || currRate > (minRate + 0.05)) {
//            ComposeDrawScaleLotteryStrategy composeDrawScaleLotteryStrategy = new ComposeDrawScaleLotteryStrategy();
//            ComposePrizeDTO composePrizeDTO = composeDrawScaleLotteryStrategy.drawPrize(prizeDTOS);
//            if (composePrizeDTO.getPrizeProbability().doubleValue() == minRate) {
//                times++;
//            }
//            allTimes++;
//            if (composePrizeDTO.getPrizeProbability().compareTo(new BigDecimal(minRate)) < 0) {
//                System.out.println("命中 生成次数 ：" + times + " 总次数： " + allTimes);
//            }
//        }
//
//    }
}
