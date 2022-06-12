package com.starnft.star.domain.wallet.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel("提现取消请求")
public class WithdrawCancelReq implements Serializable {

    private Long uid;

    @NotBlank(message = "钱包id不能为空")
    @ApiModelProperty(value = "钱包id")
    private String walletId;

    @NotBlank(message = "提现流水号不能为空")
    @ApiModelProperty(value = "提现流水号")
    private String withdrawSn;

}
