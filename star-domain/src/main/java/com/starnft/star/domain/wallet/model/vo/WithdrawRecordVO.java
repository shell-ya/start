package com.starnft.star.domain.wallet.model.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WithdrawRecordVO {

    private Long uid;
    /**
     * 钱包id
     */
    private String walletId;
    /**
     * 提现金额
     */
    private BigDecimal money;
    /**
     * 提现银行卡号
     */
    private Long bankNo;
    /**
     * 卡姓名
     */
    private String cardName;
    /*
     * 提现流水号
     */
    private String withdrawTradeNo;
    /**
     * 提现渠道
     */
    private String channel;
    /**
     * 提现状态
     */
    private Integer status;
    /**
     * 提现结果
     */
    private String applyMsg;
}
