package com.starnft.star.domain.number.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Harlan
 * @date 2022/05/24 17:34
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NumberDetailVO implements Serializable {
    private Long id;
    private Long number;
    private String name;
    private String picture;
    private String contractAddress;
    private String chainIdentification;
    private String descrption;
    private BigDecimal price;
    private Integer status;
    private Integer type;
    private String ownerBy;
}
