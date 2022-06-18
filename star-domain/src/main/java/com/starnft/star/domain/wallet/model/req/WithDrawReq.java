package com.starnft.star.domain.wallet.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("提现申请请求")
public class WithDrawReq {
    /**
     * 提现uid
     */
    @ApiModelProperty(value = "uid")
    private Long uid;
    /**
     * 钱包id
     */
    @NotNull(message = "钱包id不能为空")
    @ApiModelProperty(value = "钱包id", required = true)
    private String walletId;
    /**
     * 提现金额
     */
    @ApiModelProperty(value = "提现金额", required = true)
    @NotNull(message = "金额不能为空")
    @Pattern(regexp = "^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,2})?))$", message = "请输入正确的金额")
    private String money;
    /**
     * 提现银行卡号
     */
    @NotNull(message = "银行卡号不能为空")
    @ApiModelProperty(value = "提现银行卡号", required = true)
    @Pattern(regexp = "[1-9]\\d*", message = "卡号输入有误")
    private String bankNo;
    /**
     * 卡姓名
     */
    @NotBlank(message = "持卡人姓名不能为空")
    @ApiModelProperty(value = "卡姓名", required = true)
    private String cardName;
    /**
     * 提现渠道
     */
    @NotBlank(message = "提现渠道不能为空")
    @ApiModelProperty(value = "提现渠道", required = true)
    private String channel;

    /**
     * 支付密码校验凭证
     */
    @NotBlank(message = "支付密码校验凭证不能为空")
    @ApiModelProperty(value = "支付密码校验凭证", required = true)
    private String pwdToken;

}
