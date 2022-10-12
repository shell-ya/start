package com.starnft.star.domain.number.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ThemeDingVo {
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
}
