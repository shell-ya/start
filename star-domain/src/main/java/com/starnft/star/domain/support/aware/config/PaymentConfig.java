package com.starnft.star.domain.support.aware.config;

import lombok.Data;

@Data
public class PaymentConfig {

    private String channel;

    private String notifyUrl;
}
