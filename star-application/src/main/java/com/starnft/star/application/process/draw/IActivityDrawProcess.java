package com.starnft.star.application.process.draw;

import com.starnft.star.application.process.draw.req.DrawProcessReq;
import com.starnft.star.application.process.draw.res.DrawProcessResult;

public interface IActivityDrawProcess {


    /**
     * 执行抽奖流程
     * @param req 抽奖请求
     * @return    抽奖结果
     */
    DrawProcessResult doDrawProcess(DrawProcessReq req);


}
