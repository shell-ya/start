package com.starnft.star.application.process.draw.res;

import com.starnft.star.common.Result;
import com.starnft.star.domain.draw.model.vo.DrawAwardVO;

/**
 * @description: 活动抽奖结果
 */
public class DrawProcessResult extends Result {

    private DrawAwardVO drawAwardVO;

    public DrawProcessResult(String code, String info) {
        super(Integer.valueOf(code), info);
    }

    public DrawProcessResult(String code, String info, DrawAwardVO drawAwardVO) {
        super(Integer.valueOf(code), info);
        this.drawAwardVO = drawAwardVO;
    }

    public DrawAwardVO getDrawAwardVO() {
        return drawAwardVO;
    }

    public void setDrawAwardVO(DrawAwardVO drawAwardVO) {
        this.drawAwardVO = drawAwardVO;
    }

}
