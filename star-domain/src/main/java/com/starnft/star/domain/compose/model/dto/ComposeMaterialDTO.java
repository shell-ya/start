package com.starnft.star.domain.compose.model.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class ComposeMaterialDTO  implements Serializable {
    private Long  themeId;
    private String themeName;
    private String themeImages;
    private Integer number;
}
