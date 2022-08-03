package com.starnft.star.business.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class UserThemeVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long  themeId;
    private String themeName;
    private String themeImages;
    private Integer types;
    @JsonSerialize(using = ToStringSerializer.class)
    private String  userId;
    private Integer nums;
    private Integer status;
}
