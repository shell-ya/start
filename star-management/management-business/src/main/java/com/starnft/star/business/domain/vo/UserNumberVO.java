package com.starnft.star.business.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserNumberVO  implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Integer status;
    @JsonSerialize(using = ToStringSerializer.class)
    private String userId;

    private Integer source;

    private BigDecimal preTaxPrice;

    private BigDecimal platformTax;

    private BigDecimal copyrightTax;

    private BigDecimal afterTaxPrice;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long themeNumber;

    private String chainIdentification;


}
