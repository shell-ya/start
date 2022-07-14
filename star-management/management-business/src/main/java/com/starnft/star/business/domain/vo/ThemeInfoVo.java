package com.starnft.star.business.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Date 2022/6/27 6:04 PM
 * @Author ： shellya
 */
@Data
public class ThemeInfoVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String themeName;
    private Long stock;
    private Long goodNumber;
    private BigDecimal price;
    private Boolean disabled;

}
