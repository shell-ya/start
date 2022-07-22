package com.starnft.star.domain.wallet.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("充值结果查询请求")
public class TxResultReq implements Serializable {

    @NotNull(message = "uid不能为空")
    @ApiModelProperty(value = "uid")
    private Long uid;

    @NotBlank(message = "支付渠道不能为空")
    @ApiModelProperty(value = "支付渠道")
    private String payChannel;

    @NotBlank(message = "充值单号不能为空")
    @ApiModelProperty(value = "充值单号")
    private String orderSn;


}
