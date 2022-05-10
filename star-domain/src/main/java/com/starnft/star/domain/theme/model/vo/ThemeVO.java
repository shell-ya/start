package com.starnft.star.domain.theme.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ThemeVO {
    private Long id;
    private Boolean themeType;
    private String themeName;
    private String themePic;
    private Integer publishNumber;
    private Byte themeLevel;
    private BigDecimal lssuePrice;
    private String tags;
}
