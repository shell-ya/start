package com.starnft.star.domain.number.model.dto;

import com.starnft.star.common.enums.NumberStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class NumberBatchUpdateDTO {
    private Long uid;
    private List<Long> numberIds;
    private BigDecimal price;
    private NumberStatusEnum status;
    private Boolean isDeleted;
}
