package com.starnft.star.domain.compose.strategy;

import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ComposeDrawLotteryStrategy {
    ComposePrizeDTO drawPrize(List<ComposePrizeDTO> composePrizes, int randNum, int seed);
//    int draw(List<BigDecimal> scales);
}
