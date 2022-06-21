package com.starnft.star.domain.wallet.model.req;

import com.starnft.star.common.constant.StarConstants;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class WalletLogReq implements Serializable {

    /** userId*/
    private Long userId;
    /** 钱包id*/
    private String walletId;
    /** 变动金额*/
    private BigDecimal offset;
    /** 变动后当前金额*/
    private BigDecimal currentMoney;
    /** 渠道*/
    private String payChannel;
    /** 流水号*/
    private String orderNo;
    /** 变动时间*/
    private Date payTime;
    /** 交易类型*/
    private StarConstants.Transaction_Type transactionType;

}
