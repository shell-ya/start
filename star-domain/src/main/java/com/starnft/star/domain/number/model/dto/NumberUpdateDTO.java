package com.starnft.star.domain.number.model.dto;

import com.starnft.star.common.enums.NumberStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Harlan
 * @date 2022/05/25 20:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NumberUpdateDTO implements Serializable {
    private Long uid;
    private Long numberId;
    private BigDecimal price;
    private NumberStatusEnum status;

}
