package com.starnft.star.domain.wallet.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@ApiModel("提现申请结果")
public class WithdrawResult {

    @ApiModelProperty(value = "平台提现流水号")
    private Long withdrawTradeNo;

    @ApiModelProperty(value = "提现状态 0申请中 1提现成功 ")
    private Integer withdrawStatus;
}
