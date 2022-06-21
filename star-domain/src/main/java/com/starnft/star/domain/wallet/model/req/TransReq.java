package com.starnft.star.domain.wallet.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransReq implements Serializable {

    @NotBlank(message = "uid不能为空")
    private Long uid;

    @NotBlank(message = "orderSn不能为空")
    private String orderSn;

    @NotBlank(message = "第三方交易流水号")
    private String outTradeNo;

    @NotNull(message = "总金额不能为空 + or -")
    private BigDecimal totalAmount;

    @NotNull(message = "实付金额不能为空 + or -")
    private BigDecimal payAmount;

    @NotNull(message = "交易类型不能为空")
    private Integer tsType;

    @NotBlank(message = "渠道不能为空")
    private String payChannel;


}
