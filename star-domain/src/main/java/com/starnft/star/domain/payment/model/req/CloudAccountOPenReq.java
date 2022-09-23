package com.starnft.star.domain.payment.model.req;

import lombok.Data;

@Data
public class CloudAccountOPenReq {
    private String userId;
    private String realName;
    private String idCard;
    private String returnUri;
}
