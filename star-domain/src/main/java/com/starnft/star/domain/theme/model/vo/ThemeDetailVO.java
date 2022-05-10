package com.starnft.star.domain.theme.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ThemeDetailVO {

    private Long id;

    private Long seriesId;

    private Boolean themeType;

    private String themeName;

    private String themePic;

    private Integer publishNumber;

    private String descrption;

    private Byte themeLevel;

    private Integer stock;

    private BigDecimal lssuePrice;

    private String tags;

    private String contractAddress;
}
