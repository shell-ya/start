package com.starnft.star.domain.compose.strategy;

import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ComposeDrawLotteryStrategy {
    int drawPrize(List<ComposePrizeDTO> composePrizes);
    int draw(List<BigDecimal> scales);
}
