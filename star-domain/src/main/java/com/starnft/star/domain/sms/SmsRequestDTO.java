package com.starnft.star.domain.sms;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmsRequestDTO {
    private String phone;
    private SmsType smsType;
}
