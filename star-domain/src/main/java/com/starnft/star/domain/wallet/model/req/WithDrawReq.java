package com.starnft.star.domain.wallet.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel("提现申请请求")
public class WithDrawReq {
    /**
     * 提现uid
     */
    @NotNull(message = "uid 不能为空")
    @ApiModelProperty(value = "uid", required = false)
    private Long uid;
    /**
     * 钱包id
     */
    @NotNull(message = "钱包id不能为空")
    @ApiModelProperty(value = "钱包id", required = false)
    private String walletId;
    /**
     * 提现金额
     */
    @ApiModelProperty(value = "提现金额", required = false)
    @NotNull(message = "金额不能为空")
    private BigDecimal money;
    /**
     * 提现银行卡号
     */
    @NotNull(message = "银行卡号不能为空")
    @ApiModelProperty(value = "提现银行卡号", required = false)
    private Long bankNo;
    /**
     * 卡姓名
     */
    @NotBlank(message = "持卡人姓名不能为空")
    @ApiModelProperty(value = "卡姓名", required = false)
    private String cardName;
    /**
     * 提现渠道
     */
    @NotBlank(message = "提现渠道不能为空")
    @ApiModelProperty(value = "提现渠道", required = false)
    private String channel;

}
