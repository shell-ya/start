package com.starnft.star.domain.compose.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
@Data
public class ComposeMaterialDTO  implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long  themeId;
    private String themeName;
    private String themeImages;
    private Integer number;
}
