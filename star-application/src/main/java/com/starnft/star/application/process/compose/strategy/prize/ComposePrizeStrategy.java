package com.starnft.star.application.process.compose.strategy.prize;

import com.starnft.star.domain.compose.model.res.PrizeRes;
import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;
import com.starnft.star.domain.compose.model.req.ComposeManageReq;

public interface ComposePrizeStrategy {
    public PrizeRes composePrize(ComposeManageReq composeManageReq, ComposePrizeDTO composePrizeDTO);
}
