package com.starnft.star.domain.coupon.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/4 1:25 上午
 * @description： TODO
 */
@Data
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CouponHistoryRes {

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 卡劵标题
     */
    @ApiModelProperty("卡劵标题")
    private String composeName;

    /**
     * 卡劵封面图
     */
    @ApiModelProperty("卡劵封面图")
    private String images;

    /**
     * 有效期结束时间
     */
    @ApiModelProperty("有效期结束时间")
    private Date endAt;

    /**
     * 有效期开始时间
     */
    @ApiModelProperty("有效期开始时间")
    private Date startAt;

    /**
     * 优惠卷类型
     */
    @ApiModelProperty("优惠卷类型：1-优先购 2-手续费抵扣 3-打折 4-优惠金额")
    private Integer type;

    /**
     * 优惠卷信息id
     */
    private String couponId;

    /**
     * 优惠历史id
     */
    private Long id;

    /**
     * 获取方式
     */
    @ApiModelProperty("获取方式 1-活动 2-空投")
    private Integer getType;

    /**
     * 系列id
     */
    private Long seriesId;

    /**
     * 系列名称
     */
    @ApiModelProperty("系列名称")
    private String seriesName;

    /**
     * 主题id
     */
    private Long themeId;

    /**
     * 主题名称
     */
    @ApiModelProperty("主题名称")
    private String themeName;

    /**
     * 优惠金额/折扣
     */
    @ApiModelProperty("优惠金额/折扣")
    private BigDecimal amount;

    /**
     * 使用门槛：0-无门槛
     */
    @ApiModelProperty("使用门槛： 0-无门槛")
    private Integer minPoint;

    /**
     * 使用类型 example：0-全场通用 1-指定系列 2-指定主题
     */
    @ApiModelProperty("使用类型 0-全场通用 1-指定系列 2-指定主题")
    private Integer useType;
}
