package com.starnft.star.domain.scope.model.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddScoreRecordReq {
    private Long userId;
    private BigDecimal scope;
    private Integer mold;
    private Integer scopeType;
    private String remarks;
    private Date createdAt;
}
