package com.starnft.star.application.process.scope.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ScoreDTO {
    private Long userId;
    private BigDecimal scope;
    private Integer scopeType;
    private Boolean isSub;
    private String template;
}
