package com.starnft.star.domain.order.model.res;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderListRes implements Serializable {

    private String orderSn;

    private Integer themeNumber;

    private String themeName;

    private String themePic;

    private Integer themeType;

    private String payAmount;

    private Integer status;

    private Date createdAt;

}
