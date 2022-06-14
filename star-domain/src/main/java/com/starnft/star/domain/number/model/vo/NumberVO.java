package com.starnft.star.domain.number.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NumberVO implements Serializable {
    private Long id;
    private Long number;
    private String themeName;
    private String themePic;
    private String identification;
    private BigDecimal price;
    private Integer type;
    private Integer status;
}
