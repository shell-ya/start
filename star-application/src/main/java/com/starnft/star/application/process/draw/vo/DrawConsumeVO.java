package com.starnft.star.application.process.draw.vo;

import com.starnft.star.domain.activity.model.vo.DrawActivityVO;
import com.starnft.star.domain.activity.model.vo.DrawOrderVO;
import com.starnft.star.domain.draw.model.vo.DrawAwardVO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrawConsumeVO {

    private String numberId;

    private Integer product;

    private DrawAwardVO drawAwardVO;

    private DrawActivityVO drawActivityVO;

    private DrawOrderVO drawOrderVO;
}
