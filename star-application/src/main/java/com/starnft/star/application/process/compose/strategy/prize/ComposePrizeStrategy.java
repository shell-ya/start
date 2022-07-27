package com.starnft.star.application.process.compose.strategy.prize;

import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;
import com.starnft.star.domain.compose.model.req.ComposeManageReq;

import java.util.List;

public interface ComposePrizeStrategy {
    public void composePrize(List<UserNumbersVO> userNumbersVOList, ComposeManageReq composeManageReq, ComposePrizeDTO composePrizeDTO);
}
