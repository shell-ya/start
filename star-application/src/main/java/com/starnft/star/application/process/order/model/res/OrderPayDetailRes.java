package com.starnft.star.application.process.order.model.res;

import com.starnft.star.domain.payment.model.res.PaymentRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单支付结果响应")
public class OrderPayDetailRes {

    @ApiModelProperty(value = "订单交易状态 0成功")
    private Integer status;

    @ApiModelProperty(value = "订单号")
    private String orderSn;

    @ApiModelProperty(value = "第三方支付信息")
    private PaymentRes results;

}
