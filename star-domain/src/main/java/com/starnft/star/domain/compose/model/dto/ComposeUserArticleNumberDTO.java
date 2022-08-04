package com.starnft.star.domain.compose.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComposeUserArticleNumberDTO {
    private String  themeName;
    private Long themeId;
    private Long numberId;
    private Long  themeNumber;
    private BigDecimal price;
    private Long themeImages;
}
