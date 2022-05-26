package com.starnft.star.domain.numbers.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Harlan
 * @date 2022/05/26 21:09
 */
@Data
@Builder
public class NumberCirculationDTO implements Serializable {
    private Long numberId;
    private BigDecimal beforePrice;
    private BigDecimal afterPrice;
}