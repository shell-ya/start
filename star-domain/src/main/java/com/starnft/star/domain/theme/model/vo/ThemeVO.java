package com.starnft.star.domain.theme.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel
public class ThemeVO implements Serializable {
    @ApiModelProperty("主题id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty("主题类型")
    private Integer themeType;
    @ApiModelProperty("系列id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long seriesId;
    @ApiModelProperty("主题名称")
    private String themeName;
    @ApiModelProperty("主题图片")
    private String themePic;
    @ApiModelProperty("发行数量")
    private Integer publishNumber;
    @ApiModelProperty("主题级别")
    private Byte themeLevel;
    @ApiModelProperty("发行价格")
    private BigDecimal lssuePrice;
    @ApiModelProperty("标记")
    private String tags;
    @ApiModelProperty("发行商id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long publisherId;
    @ApiModelProperty("发行商名称")
    private String publisherName;
    @ApiModelProperty("发行商图标")
    private String publisherPic;
}
