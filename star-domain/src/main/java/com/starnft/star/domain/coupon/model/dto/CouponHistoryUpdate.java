package com.starnft.star.domain.coupon.model.dto;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/6 1:08 下午
 * @description： TODO
 */
@Data
@Accessors(chain = true)
public class CouponHistoryUpdate {

    /**
     * 主键id
     */
    private List<Long> idList;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 使用状态 1-未使用 2-已使用
     *
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
}
