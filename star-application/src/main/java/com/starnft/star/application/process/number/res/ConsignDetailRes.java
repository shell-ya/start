package com.starnft.star.application.process.number.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Harlan
 * @date 2022/06/14 23:45
 */
@Data
@Builder
@ApiModel("寄售详情")
public class ConsignDetailRes implements Serializable {
    @ApiModelProperty("商品id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty("商品编号")
    private Long number;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("商品图片")
    private String picture;
    @ApiModelProperty("价格")
    private BigDecimal price;
    @ApiModelProperty("作品版税")
    private BigDecimal copyrightRate;
    @ApiModelProperty("平台手续费")
    private BigDecimal serviceRate;
    @ApiModelProperty("涨停价格")
    private BigDecimal limitPrice;
    @ApiModelProperty("开盘价格")
    private BigDecimal openingPrice;
}
