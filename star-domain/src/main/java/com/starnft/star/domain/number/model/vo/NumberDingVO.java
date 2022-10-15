package com.starnft.star.domain.number.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NumberDingVO {
    private Long id;
    private BigDecimal price;
    private String name;
    private String image;

    /**
     * 排序 0-新增，1-跌，2-涨
     */
    private Integer rank = 0;
}
