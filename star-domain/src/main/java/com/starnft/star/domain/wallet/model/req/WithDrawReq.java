package com.starnft.star.domain.wallet.model.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class WithDrawReq {
    /**
     * 提现uid
     */
    @NotNull(message = "uid 不能为空")
    private Long uid;
    /**
     * 钱包id
     */
    @NotNull(message = "钱包id不能为空")
    private String walletId;
    /**
     * 提现金额
     */
    @NotNull(message = "金额不能为空")
    private BigDecimal money;
    /**
     * 提现银行卡号
     */
    @NotNull(message = "银行卡号不能为空")
    private Long bankNo;
    /**
     * 卡姓名
     */
    @NotBlank(message = "持卡人姓名不能为空")
    private String cardName;
    /**
     * 提现渠道
     */
    @NotBlank(message = "提现渠道不能为空")
    private String channel;

}
