package com.starnft.star.domain.payment.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotifyRes {
    private PayCheckRes payCheckRes;
    private String topic;
}
