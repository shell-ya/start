package com.starnft.star.domain.wallet.model.aggregates;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.wallet.model.vo.WalletVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RechargeRich {
    /** 变动金额*/
    private BigDecimal money;
    /** 变动后当前金额*/
    private BigDecimal currentMoney;
    /** 渠道*/
    private String payChannel;
    /** 流水号*/
    private String payNo;
    /** 变动时间*/
    private Date payTime;
    /** 交易类型*/
    private StarConstants.Transaction_Type transactionType;

    private WalletVO wallet;
}
