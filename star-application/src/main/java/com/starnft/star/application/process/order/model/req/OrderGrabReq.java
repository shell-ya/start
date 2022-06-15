package com.starnft.star.application.process.order.model.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderGrabReq implements Serializable {

    private Long userId;

    private String time;

    private Long themeId;

}
