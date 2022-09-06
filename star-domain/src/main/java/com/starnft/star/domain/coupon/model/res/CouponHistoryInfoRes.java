package com.starnft.star.domain.coupon.model.res;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/6 11:06 下午
 * @description： TODO
 */
@Data
@Accessors(chain = true)
public class CouponHistoryInfoRes {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 优惠卷id
     */
    private String couponId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 获取方式：example 1-空投 2-活动
     * 具体参考枚举
     */
    private Integer getType;

    /**
     * 使用状态 1-未使用 2-已使用
     */
    private Integer useStatus;

    /**
     * 使用时间
     */
    private Date useTime;

    /**
     * 订单id-在哪个订单使用的?
     */
    private String orderId;

    /**
     * 渠道id example: event id
     */
    private String channelId;
}
