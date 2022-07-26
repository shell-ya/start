package com.starnft.star.domain.compose.strategy;

import cn.hutool.core.util.RandomUtil;
import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component("composeDrawScaleLotteryStrategy")
public class ComposeDrawScaleLotteryStrategy implements  ComposeDrawLotteryStrategy
{
    @Override
    public int drawPrize(List<ComposePrizeDTO> composePrizes) {
        if (null != composePrizes && composePrizes.size() > 0) {
            List<BigDecimal> prizeProbabilityArray = new ArrayList<BigDecimal>(composePrizes.size());
            for (ComposePrizeDTO composePrize : composePrizes) {
                // 按顺序将概率添加到集合中
                prizeProbabilityArray.add(composePrize.getPrizeProbability());
            }
            return draw(prizeProbabilityArray);
        }
        return -1;
    }

    @Override
    public int draw(List<BigDecimal> scales) {

        List<BigDecimal> sortRateList = new ArrayList<>();

        // 计算概率总和
        BigDecimal sumRate = BigDecimal.ZERO;
        for (BigDecimal prob : scales) {
            sumRate= sumRate.add(prob);
        }

        if (sumRate.compareTo(BigDecimal.ZERO)!=0) {
            BigDecimal rate = BigDecimal.ZERO; // 概率所占比例
            for (BigDecimal prob : scales) {
                rate = rate.add(prob);
                // 构建一个比例区段组成的集合(避免概率和不为1)
                sortRateList.add(rate.divide(sumRate,BigDecimal.ROUND_HALF_UP));
            }
            // 随机生成一个随机数，并排序
            BigDecimal random = RandomUtil.randomBigDecimal(BigDecimal.ZERO, BigDecimal.ONE);
            sortRateList.add(random);
            Collections.sort(sortRateList);
            // 返回该随机数在比例集合中的索引
            return sortRateList.indexOf(random);
        }
        return 0;
    }


    public static void main(String[] args) {
        List<ComposePrizeDTO> gifts = new ArrayList<ComposePrizeDTO>();
        // 序号==物品Id==物品名称==概率
        gifts.add(ComposePrizeDTO
                .builder()
                .composeId(1L)
                .prizeType(1)
                .prizeNumber(new BigDecimal(500))
                .currentPrizeNumber(new BigDecimal(500))
                .prizeProbability(new BigDecimal(0.01))
                .id(1L)
                .prizeStamp("1").build());
        gifts.add(ComposePrizeDTO
                .builder()
                .composeId(1L)
                .prizeType(1)
                .prizeNumber(new BigDecimal(500))
                .currentPrizeNumber(new BigDecimal(500))
                .prizeProbability(new BigDecimal(0.09))
                .id(2L)
                .prizeStamp("2").build());
          gifts.add(ComposePrizeDTO
                .builder()
                .composeId(1L)
                .prizeType(1)
                .prizeNumber(new BigDecimal(500))
                .currentPrizeNumber(new BigDecimal(500))
                .prizeProbability(new BigDecimal(0.8))
                .id(3L)
                .prizeStamp("3").build());
          gifts.add(ComposePrizeDTO
                .builder()
                .composeId(1L)
                .prizeType(1)
                .prizeNumber(new BigDecimal(500))
                .currentPrizeNumber(new BigDecimal(500))
                .prizeProbability(new BigDecimal(0.1))
                .id(4L)
                .prizeStamp("4").build());

        List<BigDecimal> orignalRates = new ArrayList<BigDecimal>(gifts.size());
        for (ComposePrizeDTO gift : gifts) {
            BigDecimal probability = gift.getPrizeProbability();
            if (probability.compareTo(BigDecimal.ZERO)<0) {
                probability = BigDecimal.ZERO;
            }
            orignalRates.add(probability);
        }
        ComposeDrawScaleLotteryStrategy composeDrawScaleLotteryStrategy = new ComposeDrawScaleLotteryStrategy();
        // statistics
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        double num = 1000000;
        for (int i = 0; i < num; i++) {
            int orignalIndex = composeDrawScaleLotteryStrategy.drawPrize(gifts);

            Integer value = count.get(orignalIndex);
            count.put(orignalIndex, value == null ? 1 : value + 1);
        }

        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            System.out.println(gifts.get(entry.getKey()) + ", count=" + entry.getValue() + ", probability="
                    + entry.getValue() / num);
        }
    }
}
