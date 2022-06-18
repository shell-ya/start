package com.starnft.star.application.process.order.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class OrderGrabStatus implements Serializable {

    private Integer status;

    private String orderSn;

    private String creatTime;


}
