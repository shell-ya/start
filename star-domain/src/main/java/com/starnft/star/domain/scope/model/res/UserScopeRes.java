package com.starnft.star.domain.scope.model.res;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserScopeRes {
    private Long userId;
    private BigDecimal scope;

}
