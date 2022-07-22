package com.starnft.star.business.domain.vo;

import lombok.Data;

@Data
public class UserThemeVO {
    private Long  themeId;
    private String themeName;
    private String themeImages;
    private Integer types;
    private String  userId;
    private Integer nums;
    private Integer status;
}
