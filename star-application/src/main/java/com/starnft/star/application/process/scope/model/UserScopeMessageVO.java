package com.starnft.star.application.process.scope.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserScopeMessageVO implements Serializable {
    private Integer eventGroup;
    private BigDecimal scope;
    private Integer scopeType;
    private Long userId;
}
