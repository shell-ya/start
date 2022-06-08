package com.starnft.star.domain.order.service.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderPlaceReq implements Serializable {

    /**
     * 主题信息
     */
    @ApiModelProperty(name = "主题信息", notes = "")
    private Long seriesThemeInfoId;
    /**
     * 主题编号信息id
     */
    @ApiModelProperty(name = "主题编号信息id", notes = "")
    private Long seriesThemeId;
    /**
     * 系列id
     */
    @ApiModelProperty(name = "系列id", notes = "")
    private Long seriesId;
    /**
     * 系列名称
     */
    @ApiModelProperty(name = "系列名称", notes = "")
    private String seriesName;
    /**
     * 主题名称
     */
    @ApiModelProperty(name = "主题名称", notes = "")
    private String themeName;
    /**
     * 主题图片
     */
    @ApiModelProperty(name = "主题图片", notes = "")
    private String themePic;
    /**
     * 主题类型：1-藏品;2-盲盒
     */
    @ApiModelProperty(name = "主题类型：1-藏品", notes = "2-盲盒")
    private Integer themeType;
    /**
     * 用户id
     */
    @ApiModelProperty(name = "用户id", notes = "")
    private Long userId;
    /**
     * 订单总金额
     */
    @ApiModelProperty(name = "订单总金额", notes = "")
    private BigDecimal totalAmount;
    /**
     * 应付金额
     */
    @ApiModelProperty(name = "应付金额", notes = "")
    private BigDecimal payAmount;
}
