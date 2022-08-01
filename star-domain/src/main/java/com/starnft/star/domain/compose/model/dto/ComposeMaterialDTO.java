package com.starnft.star.domain.compose.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel
public class ComposeMaterialDTO  implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主题id")
    private Long  themeId;
    @ApiModelProperty("主题名称")
    private String themeName;
    @ApiModelProperty("主题图片")
    private String themeImages;
    @ApiModelProperty("需要数量")
    private Integer number;
}
