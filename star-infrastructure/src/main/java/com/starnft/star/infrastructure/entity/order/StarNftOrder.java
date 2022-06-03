package com.starnft.star.infrastructure.entity.order;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单表;
 * @author : Ryanz
 * @date : 2022-5-23
 */
@ApiModel(value = "订单表",description = "")
@TableName("star_nft_order")
public class StarNftOrder extends BaseEntity implements Serializable {

    /** 主键id */
    @ApiModelProperty(name = "主键id",notes = "")
    @TableId
    private Long id ;
    /** 订单编号 */
    @ApiModelProperty(name = "订单编号",notes = "")
    private String orderSn ;
    /** 主题信息 */
    @ApiModelProperty(name = "主题信息",notes = "")
    private Long seriesThemeInfoId ;
    /** 主题编号信息id */
    @ApiModelProperty(name = "主题编号信息id",notes = "")
    private Long seriesThemeId ;
    /** 系列id */
    @ApiModelProperty(name = "系列id",notes = "")
    private Long seriesId ;
    /** 系列名称 */
    @ApiModelProperty(name = "系列名称",notes = "")
    private String seriesName ;
    /** 主题名称 */
    @ApiModelProperty(name = "主题名称",notes = "")
    private String themeName ;
    /** 主题图片 */
    @ApiModelProperty(name = "主题图片",notes = "")
    private String themePic ;
    /** 主题类型：1-藏品;2-盲盒 */
    @ApiModelProperty(name = "主题类型：1-藏品",notes = "2-盲盒")
    private Integer themeType ;
    /** 状态(0-进行中;1-已完成) */
    @ApiModelProperty(name = "状态(0-进行中",notes = "1-已完成)")
    private Integer status ;
    /** 用户id */
    @ApiModelProperty(name = "用户id",notes = "")
    private Long userId ;
    /** 订单总金额 */
    @ApiModelProperty(name = "订单总金额",notes = "")
    private BigDecimal totalAmount ;
    /** 应付金额 */
    @ApiModelProperty(name = "应付金额",notes = "")
    private BigDecimal payAmount ;
    /** 支付单编号 */
    @ApiModelProperty(name = "支付单编号",notes = "")
    private String payNumber ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getSeriesThemeInfoId() {
        return seriesThemeInfoId;
    }

    public void setSeriesThemeInfoId(Long seriesThemeInfoId) {
        this.seriesThemeInfoId = seriesThemeInfoId;
    }

    public Long getSeriesThemeId() {
        return seriesThemeId;
    }

    public void setSeriesThemeId(Long seriesThemeId) {
        this.seriesThemeId = seriesThemeId;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemePic() {
        return themePic;
    }

    public void setThemePic(String themePic) {
        this.themePic = themePic;
    }

    public Integer getThemeType() {
        return themeType;
    }

    public void setThemeType(Integer themeType) {
        this.themeType = themeType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }
}
