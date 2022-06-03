package com.starnft.star.domain.order.model.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class OrderVO implements Serializable {

    private Long userId;
    /** 订单编号 */
    private String orderSn;
    /** 主题信息 */
    private Long seriesThemeId;
    /** 主题编号信息id */
    private Long seriesThemeInfoId;
    /** 系列id */
    private Long seriesId;
    /** 系列名称 */
    private String seriesName;
    /** 主题名称 */
    private String themeName ;
    /** 主题图片 */
    private String themePic ;
    /** 主题类型：1-藏品;2-盲盒 */
    private Integer themeType ;
    /** 订单总金额 */
    private BigDecimal totalAmount ;
    /** 应付金额 */
    private BigDecimal payAmount ;


}
