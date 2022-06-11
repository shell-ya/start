package com.starnft.star.domain.notify.model.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotifyOrderReq implements Serializable {

    private Long uid;

    private String orderSn;

    private String transSn;

    private BigDecimal totalAmount;

    private String payChannel;

    private String message;


    private Integer status;


    private Date createTime;

    private Date payTime;
}
