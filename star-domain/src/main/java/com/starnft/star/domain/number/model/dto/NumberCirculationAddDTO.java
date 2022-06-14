package com.starnft.star.domain.number.model.dto;

import com.starnft.star.common.enums.NumberCirculationTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Harlan
 * @date 2022/05/26 16:43
 */
@Data
@Builder
public class NumberCirculationAddDTO implements Serializable {
    private Long uid;
    private Long numberId;
    private NumberCirculationTypeEnum type;
    private BigDecimal beforePrice;
    private BigDecimal afterPrice;
}
