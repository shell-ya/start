package com.starnft.star.application.process.props.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("道具购买请求")
public class PropBuyReq {

    @ApiModelProperty(value = "uid", required = false)
    private Long uid;

    @ApiModelProperty(value = "道具id", required = true)
    @NotNull
    private String propId;

    @ApiModelProperty(value = "购买数量", required = true)
    @NotNull
    private Integer numbers;

    @ApiModelProperty(value = "支付密码校验凭证", required = true)
//    @NotBlank
    private String payToken;
}
