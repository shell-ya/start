package com.starnft.star.application.process.compose.strategy.lottery;

import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;

import java.util.List;

public interface ComposeDrawLotteryStrategy {
    ComposePrizeDTO drawPrize(List<ComposePrizeDTO> composePrizes);
//    int draw(List<BigDecimal> scales);
}
