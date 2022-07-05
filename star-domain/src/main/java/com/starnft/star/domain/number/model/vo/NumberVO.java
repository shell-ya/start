package com.starnft.star.domain.number.model.vo;

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
@Data
@Builder
@ApiModel
public class NumberVO implements Serializable {
    @ApiModelProperty("商品id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty("商品编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long number;
    @ApiModelProperty("主题id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long themeId;
    @ApiModelProperty("发行数量")
    private Integer issuedQty;
    @ApiModelProperty("商品名称")
    private String themeName;
    @ApiModelProperty("商品图片")
    private String themePic;
    @ApiModelProperty("标识")
    private String identification;
    @ApiModelProperty("商品价格")
    private BigDecimal price;
    @ApiModelProperty("商品类型(1-藏品 2-盲盒")
    private Integer type;
    @ApiModelProperty("商品状态 状态(0-未出售  1-已出售  2-挂售中)")
    private Integer status;
    @ApiModelProperty("交易状态 0-未交易  1-交易中")
    private Integer isTransaction;
}
