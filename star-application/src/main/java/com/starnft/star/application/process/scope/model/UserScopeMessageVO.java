package com.starnft.star.application.process.scope.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserScopeMessageVO implements Serializable {
    private Integer eventCode;
    private BigDecimal scope;
    private Long userId;
}
