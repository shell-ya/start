package com.starnft.star.domain.sms.configs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwMessageInfo {
    private String swMessageApi;
    private String swMessageAppSecret;
    private String swMessageAppKey;
    private String swMessageAppCode;
    private String swMessageHeader;
}
