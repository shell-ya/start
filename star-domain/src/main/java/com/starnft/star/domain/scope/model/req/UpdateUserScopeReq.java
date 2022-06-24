package com.starnft.star.domain.scope.model.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateUserScopeReq {
    private Long userId;
    private Integer scopeType;
    private BigDecimal scope;
    private Integer version;
}
