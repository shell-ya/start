package com.starnft.star.domain.compose.strategy;

import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component("composeDrawScaleLotteryStrategy")
public class ComposeDrawScaleLotteryStrategy implements  ComposeDrawLotteryStrategy
{
    @Override
    public int drawPrize(List<ComposePrizeDTO> composePrizes) {
        return 0;
    }

    @Override
    public int draw(List<BigDecimal> scales) {
        return 0;
    }
}
