package com.starnft.star.domain.coupon.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/9 9:24 上午
 * @description： TODO
 */
@Data
public class CouponInfoUpdate {

    /**
     * 优惠卷id
     */
    private String couponId;

    /**
     * 优惠卷名称
     */
    private String name;

    /**
     * 优惠卷图片
     */
    private String image;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 优惠金额/折扣
     */
    private BigDecimal amount;

    /**
     * 每人限领几张
     */
    private Integer preLimit;

    /**
     * 使用门槛 example：0-无门槛
     */
    private Integer minPoint;

    /**
     * 有效期开始时间
     */
    private Date startTime;

    /**
     * 有效期结束时间
     */
    private Date endTime;

    /**
     * 使用类型 example：0-全场通用 1-指定系列 2-指定主题
     */
    private Integer useType;

    /**
     * 备注
     */
    private String note;

    /**
     * 发行数量
     */
    private Integer publicCount;

    /**
     * 使用数量
     */
    private Integer useCount;

    /**
     * 领取数量
     */
    private Integer receiveCount;

    /**
     * 可以领取的日期
     */
    private Date enableTime;
}
