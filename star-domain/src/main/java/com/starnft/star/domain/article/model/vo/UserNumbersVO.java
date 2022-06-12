package com.starnft.star.domain.article.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class UserNumbersVO {
    @ApiModelProperty("主题id")
    private Long themeId;
    @ApiModelProperty("主题商品id")
    private Long numberId;
    @ApiModelProperty("主题商品编号")
    private Long themeNumber;
    @ApiModelProperty("主题名称")
    private String themeName;
    @ApiModelProperty("主题图片")
    private String themeImages;
    @ApiModelProperty("购买价格")
    private BigDecimal price;
}
