package com.starnft.star.business.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Date 2022/7/15 7:57 PM
 * @Author ： shellya
 */
@Data
public class WithDrawDetail {
    /** id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long withdrawUid;
    /** 提现金额 */
    private BigDecimal withdrawMoney;
    /** 提现流水号 */
    private String withdrawTradeNo;
    /** 提现银行卡号 */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long withdrawBankNo;
    private String bankMatchName;
    private String withdrawChannel;
    private String applyTime;
    private String applyPassTime;
    private Long applyStatus;
    private String applyMsg;
    private BigDecimal teCost;
    private BigDecimal fee;

}
