package com.starnft.star.domain.message;

import lombok.Data;

@Data
public class SmsConfigInfo {

    private String swApi;

    private String swAppSecret;

    private String swAppCode;

    private String swAppKey;

    private String swMsgHeader;
}
