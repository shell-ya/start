package com.starnft.star.domain.number.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserThemeMappingVO implements Serializable {


    /**
     * 主题信息id
     */
    @ApiModelProperty(value = "主题信息id")
    private Long seriesThemeInfoId;

    /**
     * 主题编号信息id
     */
    @ApiModelProperty(value = "主题编号信息id")
    private Long seriesThemeId;

    /**
     * 状态(0-已购买 1-挂售中 2-已出售 )
     */
    @ApiModelProperty(value = "状态(0-已购买 1-挂售中 2-已出售 )")
    private Integer status;

    /**
     * 拥有者
     */
    @ApiModelProperty(value = "拥有者")
    private String userId;

    /**
     * 购买来源(1-藏品 2-盲盒)
     */
    @ApiModelProperty(value = "购买来源(1-藏品 2-盲盒)")
    private Integer source;

    /**
     * 税前价格
     */
    @ApiModelProperty(value = "税前价格")
    private BigDecimal preTaxPrice;

    /**
     * 平台税
     */
    @ApiModelProperty(value = "平台税")
    private BigDecimal platformTax;

    /**
     * 版权税
     */
    @ApiModelProperty(value = "版权税")
    private BigDecimal copyrightTax;

    /**
     * 税后价格
     */
    @ApiModelProperty(value = "税后价格")
    private BigDecimal afterTaxPrice;

}
