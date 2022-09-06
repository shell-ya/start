package com.starnft.star.domain.coupon.model.req;

import lombok.Data;
import sun.util.resources.ga.LocaleNames_ga;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/6 9:35 下午
 * @description： TODO
 */
@Data
public class CouponHistoryReq {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 使用状态
     */
    private Integer useStatus;

    /**
     * 系列id
     */
    private Long seriesId;

    /**
     * 主题id
     */
    private Long themeId;

    /**
     * 使用门槛 0-无门槛
     */
    private Integer minPoint;
}
