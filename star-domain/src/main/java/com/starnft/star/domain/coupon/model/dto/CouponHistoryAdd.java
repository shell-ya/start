package com.starnft.star.domain.coupon.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/6 1:08 下午
 * @description： TODO
 */
@Data
@Accessors(chain = true)
public class CouponHistoryAdd {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 卡劵id
     */
    private String couponId;

    /**
     * 获取卡劵类型
     * 参考枚举：CouponGetType.java
     */
    private Integer getType;

    /**
     * 渠道id example: event id
     * 该参数可为空
     */
    private String channelId;
}
