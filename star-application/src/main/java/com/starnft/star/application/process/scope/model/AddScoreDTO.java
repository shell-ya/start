package com.starnft.star.application.process.scope.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class AddScoreDTO {
    private Long userId;
    private BigDecimal scope;
    private Integer scopeType;
    private String template;
}
