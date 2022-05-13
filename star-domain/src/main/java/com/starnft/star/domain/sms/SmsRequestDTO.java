package com.starnft.star.domain.sms;

import cn.lsnft.enums.SmsType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmsRequestDTO {
    private String phone;
    private SmsType smsType;
}
