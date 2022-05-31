package com.starnft.star.domain.wallet.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RechargeVO implements Serializable {

    @NotBlank(message = "uid不能为空")
    private String uid;

    @NotBlank(message = "orderSn不能为空")
    private String orderSn;

    @NotBlank(message = "transSn不能为空")
    private String transSn;

    @NotNull(message = "金额不能为空")
    private BigDecimal totalAmount;

    @NotBlank(message = "渠道不能为空")
    private String payChannel;

}
