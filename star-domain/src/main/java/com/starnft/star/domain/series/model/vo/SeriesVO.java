package com.starnft.star.domain.series.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel(value = "系列数据")
public class SeriesVO implements Serializable {
    @ApiModelProperty(value = "系列id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty(value = "系列名称")
    private String seriesName;
    @ApiModelProperty(value = "系列类型 1-藏品 2-盲盒")
    private Integer seriesType;
    @ApiModelProperty(value = "系列图片")
    private String seriesImages;
    @ApiModelProperty(value = "模型地址")
    private String seriesModels;
    @ApiModelProperty("发行状态 1-未发行 2-已发行 3-发行结束")
    private Integer seriesStatus;
    @ApiModelProperty("系列描述")
    private String seriesDescrption;
}
