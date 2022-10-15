package com.starnft.star.domain.number.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RaisingTheme {
    //主题id
    private Long themeInfoId;
    //主题名
    private String themeName;
    //开盘价
    private BigDecimal floorPrice;
    //涨停价
    private BigDecimal limitPrice;
    //是否涨停
    private Boolean isRaising;
}
