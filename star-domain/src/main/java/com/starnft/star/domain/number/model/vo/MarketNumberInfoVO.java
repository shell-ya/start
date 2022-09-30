package com.starnft.star.domain.number.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "市场编号信息")
public class MarketNumberInfoVO implements Serializable {

    @ApiModelProperty(value = "number ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long numId;

    @ApiModelProperty(value = "主题编号")
    private Integer themeNumber;

    @ApiModelProperty(value = "技术标识")
    private String chainIdentification;

    @ApiModelProperty(value = "藏品名称")
    private String themeName;

    @ApiModelProperty(value = "出售人")
    private Long ownerBy;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "发行数量")
    private Integer publishNumber;

    @ApiModelProperty(value = "藏品状态 0 未锁定 1 已锁定")
    private Integer status;
}
