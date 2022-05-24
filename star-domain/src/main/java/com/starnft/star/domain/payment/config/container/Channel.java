package com.starnft.star.domain.payment.config.container;

import lombok.Data;

import java.util.Map;

@Data
public class Channel {

    private String channelName;

    private String vendor;

    private Map<String,String> properties;
}
