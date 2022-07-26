package com.starnft.star.application.process.compose.strategy.lottery;

import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component("composeDrawScaleLotteryStrategy")
public class ComposeDrawScaleLotteryStrategy implements ComposeDrawLotteryStrategy
{
    @Override
    public ComposePrizeDTO drawPrize(List<ComposePrizeDTO> composePrizes, int randNum, int seed) {
        composePrizes.sort((e1, e2) -> e2.getPrizeProbability().compareTo(e1.getPrizeProbability()));
        int index = 0;
        for (int i = 0; i < composePrizes.size(); i++) {
            BigDecimal lowLimit =BigDecimal.ZERO;
            BigDecimal upLimit =BigDecimal.ZERO;
            // 概率最大的奖品中奖逻辑：随机数是否在 0 到 概率×种子 的数之间
            if (i == 0) {
                double winRate =composePrizes.get(i).getPrizeProbability().doubleValue() / 100;
                if (0 < randNum && randNum <= winRate * seed) {
                    index = i;
                }
            } else {
                // lowLimit 累计了 0~i 之间的奖品概率值
                for (int j = 0; j < i; j++) {
                    lowLimit =lowLimit.add(composePrizes.get(i).getPrizeProbability().divide(new BigDecimal(100),BigDecimal.ROUND_HALF_UP));
                }
                // upLimit 累计了 0~i+1 之间的奖品概率值
                for (int k = 0; k < i + 1; k++) {
                    upLimit =upLimit.add(composePrizes.get(i).getPrizeProbability().divide(new BigDecimal(100),BigDecimal.ROUND_HALF_UP));
                }
                // upLimit - lowLimit == 当前 i 的中奖概率值
                // i > 0 对应下标的奖品中奖逻辑：随机数在 低累计值×种子 到 高累计值×种子 的数之间
                if (lowLimit.multiply(new BigDecimal(seed)).compareTo(new BigDecimal(randNum))<0 && new BigDecimal(randNum).compareTo(upLimit.multiply(new BigDecimal(seed)))<=0 ) {
                    index = i;
                }
            }
        }
        // 如果没有中奖，则index=0即为奖品列表中概率最大的奖品(比如优惠券，可以促进用户消费)
        return composePrizes.get(index);
    }

//    @Override
//    public int draw(List<BigDecimal> scales) {
//
//        List<BigDecimal> sortRateList = new ArrayList<>();
//
//        // 计算概率总和
//        BigDecimal sumRate = BigDecimal.ZERO;
//        for (BigDecimal prob : scales) {
//            sumRate= sumRate.add(prob);
//        }
//
//        if (sumRate.compareTo(BigDecimal.ZERO)!=0) {
//            BigDecimal rate = BigDecimal.ZERO; // 概率所占比例
//            for (BigDecimal prob : scales) {
//                rate = rate.add(prob);
//                // 构建一个比例区段组成的集合(避免概率和不为1)
//                sortRateList.add(rate.divide(sumRate,BigDecimal.ROUND_HALF_UP));
//            }
//            // 随机生成一个随机数，并排序
//            BigDecimal random = RandomUtil.randomBigDecimal(BigDecimal.ZERO, BigDecimal.ONE);
//            sortRateList.add(random);
//            Collections.sort(sortRateList);
//            // 返回该随机数在比例集合中的索引
//            return sortRateList.indexOf(random);
//        }
//        return 0;
//    }


    public static void main(String[] args) {
        List<ComposePrizeDTO> gifts = new ArrayList<ComposePrizeDTO>();
        // 序号==物品Id==物品名称==概率
        gifts.add(ComposePrizeDTO
                .builder()
                .composeId(1L)
                .prizeType(1)
                .prizeNumber(new BigDecimal(500))
                .currentPrizeNumber(new BigDecimal(500))
                .prizeProbability(new BigDecimal(5))
                .id(1L)
                .prizeStamp("5比例").build());
        gifts.add(ComposePrizeDTO
                .builder()
                .composeId(1L)
                .prizeType(1)
                .prizeNumber(new BigDecimal(500))
                .currentPrizeNumber(new BigDecimal(500))
                .prizeProbability(new BigDecimal(30))
                .id(2L)
                .prizeStamp("30比例").build());
        gifts.add(ComposePrizeDTO
                .builder()
                .composeId(1L)
                .prizeType(1)
                .prizeNumber(new BigDecimal(500))
                .currentPrizeNumber(new BigDecimal(500))
                .prizeProbability(new BigDecimal(20))
                .id(3L)
                .prizeStamp("20比例").build());
        gifts.add(ComposePrizeDTO
                .builder()
                .composeId(1L)
                .prizeType(1)
                .prizeNumber(new BigDecimal(500))
                .currentPrizeNumber(new BigDecimal(500))
                .prizeProbability(new BigDecimal(10))
                .id(4L)
                .prizeStamp("10比例").build());
        ComposeDrawScaleLotteryStrategy composeDrawScaleLotteryStrategy = new ComposeDrawScaleLotteryStrategy();
        int seed = 100000;
        Random random = new Random();
        int randNum = random.nextInt(seed);
        for (int i = 0; i < 100000; i++) {
            ComposePrizeDTO composePrizeDTO = composeDrawScaleLotteryStrategy.drawPrize(gifts, randNum, seed);
            System.out.println(composePrizeDTO.getPrizeStamp());
        }

//        List<BigDecimal> orignalRates = new ArrayList<BigDecimal>(gifts.size());
//        for (ComposePrizeDTO gift : gifts) {
//            BigDecimal probability = gift.getPrizeProbability();
//            if (probability.compareTo(BigDecimal.ZERO)<0) {
//                probability = BigDecimal.ZERO;
//            }
//            orignalRates.add(probability);
//        }
//        ComposeDrawScaleLotteryStrategy composeDrawScaleLotteryStrategy = new ComposeDrawScaleLotteryStrategy();
//        // statistics
//        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
//        double num = 1000000;
//        for (int i = 0; i < num; i++) {
//            int orignalIndex = composeDrawScaleLotteryStrategy.drawPrize(gifts);
//
//            Integer value = count.get(orignalIndex);
//            count.put(orignalIndex, value == null ? 1 : value + 1);
//        }
//
//        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
//            System.out.println(gifts.get(entry.getKey()) + ", count=" + entry.getValue() + ", probability="
//                    + entry.getValue() / num);
//        }
//    }
    }
}
