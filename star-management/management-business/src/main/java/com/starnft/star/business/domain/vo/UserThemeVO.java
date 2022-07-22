package com.starnft.star.business.domain.vo;

import lombok.Data;

@Data
public class UserThemeVO {
    private Long  themeId;
    private String themeName;
    private String themeImages;
    private Integer types;
    private Integer nums;
    private Integer status;
}
