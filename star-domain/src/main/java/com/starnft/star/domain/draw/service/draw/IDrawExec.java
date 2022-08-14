package com.starnft.star.domain.draw.service.draw;


import com.starnft.star.domain.draw.model.req.DrawReq;
import com.starnft.star.domain.draw.model.res.DrawResult;

/**
 * @description: 抽奖执行接口
 */
public interface IDrawExec {

    /**
     * 抽奖方法
     * @param req 抽奖参数；用户ID、策略ID
     * @return    中奖结果
     */
    DrawResult doDrawExec(DrawReq req);

}
