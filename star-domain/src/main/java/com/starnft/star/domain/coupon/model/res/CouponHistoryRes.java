package com.starnft.star.domain.coupon.model.res;

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
public class CouponHistoryRes {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 卡劵标题
     */
    private String composeName;

    /**
     * 卡劵封面图
     */
    private String images;

    /**
     * 有效期结束时间
     */
    private Date endAt;

    /**
     * 有效期开始时间
     */
    private Date startAt;

    /**
     * 优惠卷类型
     */
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
    private Integer getType;

    /**
     * 系列id
     */
    private Long seriesId;

    /**
     * 主题id
     */
    private Long themeId;

    /**
     * 优惠金额/折扣
     */
    private BigDecimal amount;

    /**
     * 使用门槛：0-无门槛
     */
    private Integer minPoint;

    /**
     * 使用类型 example：0-全场通用 1-指定系列 2-指定主题
     */
    private Integer useType;
}
