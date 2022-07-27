package com.starnft.star.application.process.scope.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class SubScoreDTO implements Serializable {
    private Long userId;
    private BigDecimal scope;
    private Integer scopeType;
    private String template;
}
