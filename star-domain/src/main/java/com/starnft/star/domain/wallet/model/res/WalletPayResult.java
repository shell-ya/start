package com.starnft.star.domain.wallet.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class WalletPayResult implements Serializable {

    private String orderSn;

    private String outTradeNo;

    private Integer status;

    private Date payTime;
}
