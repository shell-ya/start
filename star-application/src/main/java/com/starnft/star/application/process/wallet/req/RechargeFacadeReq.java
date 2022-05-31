package com.starnft.star.application.process.wallet.req;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RechargeFacadeReq implements Serializable {

    @NotNull(message = "userId 不能为空")
    private Long userId;

    @NotBlank(message = "钱包id不能为空")
    private String walletId;

    @NotNull(message = "金额不能为空")
    private BigDecimal money;

    @NotBlank(message = "渠道不能为空")
    private String channel;
}
