package com.starnft.star.application.process.wallet.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel("充值请求")
public class RechargeFacadeReq implements Serializable {

//    @NotNull(message = "userId 不能为空")
    private Long userId;

    @ApiModelProperty(value = "钱包id", required = true)
    private String walletId;

    @NotNull(message = "金额不能为空")
    @ApiModelProperty(value = "金额", required = true)
    private BigDecimal money;

    @NotNull(message = "银行卡号不能为空")
    @ApiModelProperty(value = "银行卡号", required = true)
    private Long cardNo;

    @NotNull(message = "跳转地址不能为空")
    @ApiModelProperty(value = "跳转地址", required = true)
    private String forward;

    @NotBlank(message = "渠道不能为空")
    @ApiModelProperty(value = "渠道 AliPay 支付宝/UNION_PAY 云闪付/WeChatPay 微信/BankCard 银行卡/Balance 余额", required = true)
    private String channel;


}
