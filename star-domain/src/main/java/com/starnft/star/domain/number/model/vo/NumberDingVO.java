package com.starnft.star.domain.number.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NumberDingVO {
    private BigDecimal price;
    private String name;
    private String image;
}
