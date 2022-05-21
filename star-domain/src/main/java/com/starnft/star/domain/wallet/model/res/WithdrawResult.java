package com.starnft.star.domain.wallet.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WithdrawResult {

    private Long withdrawTradeNo;

    private Integer withdrawStatus;
}
