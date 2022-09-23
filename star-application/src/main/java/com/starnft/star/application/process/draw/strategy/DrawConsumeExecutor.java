package com.starnft.star.application.process.draw.strategy;

import com.starnft.star.application.process.draw.req.ConsumablesVerifyReq;
import com.starnft.star.application.process.draw.vo.DrawConsumeVO;

public interface DrawConsumeExecutor {

    Boolean doConsume(DrawConsumeVO consumeVO);

    void doVerify(ConsumablesVerifyReq req);
}
