package com.starnft.star.domain.payment.model.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayCheckReq {

    private String orderSn;

}
