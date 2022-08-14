package com.starnft.star.domain.article.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNumbersVO {
    @ApiModelProperty("主题id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long themeId;
    @ApiModelProperty("主题商品id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long numberId;
    @ApiModelProperty("主题商品编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long themeNumber;
    @ApiModelProperty("发行数量")
    private Integer issuedQty;
    @ApiModelProperty("主题名称")
    private String themeName;
    @ApiModelProperty("主题图片")
    private String themeImages;
    @ApiModelProperty("买入价格")
    private BigDecimal starPrice;
    @ApiModelProperty("购买价格")
    private BigDecimal price;
    @ApiModelProperty("状态")
    private Integer status;
}
