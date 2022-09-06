package com.starnft.star.application.process.draw.strategy;

import com.starnft.star.application.process.draw.vo.DrawConsumeVO;
import com.starnft.star.domain.number.serivce.INumberService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("blindBoxConsumeExec")
public class BlindBoxConsumeExec implements DrawConsumeExecutor {

    @Resource
    private INumberService iNumberService;

    @Override
    public Boolean doConsume(DrawConsumeVO consumeVO) {
        return iNumberService.comsumeNumber(Long.parseLong(consumeVO.getDrawAwardVO().getuId()), Long.parseLong(consumeVO.getNumberId()));
    }
}
