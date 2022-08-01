package com.starnft.star.business.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ComposeMaterial {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long  themeId;
    private String themeName;
    private String themeImages;
    private Integer number;
}
