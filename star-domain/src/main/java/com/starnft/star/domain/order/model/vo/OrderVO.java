package com.starnft.star.domain.order.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单内容")
public class OrderVO implements Serializable {

    @ApiModelProperty(value = "userId")
    private Long userId;
    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(name = "发行商id", notes = "")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long publisherId;
    /**
     * 主题信息
     */
    @ApiModelProperty(value = "主题信息")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long seriesThemeId;
    /**
     * 主题编号信息id
     */
    @ApiModelProperty(value = "主题编号信息id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long seriesThemeInfoId;
    /**
     * 系列id
     */
    @ApiModelProperty(value = "系列id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long seriesId;
    /**
     * 藏品id
     */
    @ApiModelProperty(value = "藏品id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long numberId;
    /**
     * 主题编号
     */
    @ApiModelProperty(value = "主题编号")
    private Integer themeNumber;
    /**
     * 系列名称
     */
    @ApiModelProperty(value = "系列名称")
    private String seriesName;
    /**
     * 主题名称
     */
    @ApiModelProperty(value = "主题名称")
    private String themeName;
    /**
     * 主题图片
     */
    @ApiModelProperty(value = "主题图片")
    private String themePic;
    /**
     * 主题类型：1-藏品;2-盲盒
     */
    @ApiModelProperty(value = "主题类型：1-藏品;2-盲盒")
    private Integer themeType;
    /**
     * 订单总金额
     */
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal totalAmount;
    /**
     * 应付金额
     */
    @ApiModelProperty(value = "应付金额")
    private BigDecimal payAmount;
    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdAt;

    @ApiModelProperty(value = "过期时间 s")
    private Long expire;

    @ApiModelProperty(value = "订单类型 PUBLISH_GOODS 发行 、MARKET_GOODS 市场订单")
    private String orderType;

    @ApiModelProperty(name = "发行商名称", notes = "")
    private String publisherName;


    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
