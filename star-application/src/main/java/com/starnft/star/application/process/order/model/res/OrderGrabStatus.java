package com.starnft.star.application.process.order.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderGrabStatus {

    private Integer status;

    private String orderSn;

    private String creatTime;


}
