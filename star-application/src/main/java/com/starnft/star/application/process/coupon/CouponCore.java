package com.starnft.star.application.process.coupon;


import com.starnft.star.domain.coupon.model.dto.CouponHistoryAdd;
import com.starnft.star.domain.coupon.model.dto.CouponHistoryUpdate;
import com.starnft.star.domain.coupon.model.res.CouponHistoryRes;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/4 1:25 上午
 * @description： TODO
 */
public interface CouponCore {

    /**
     * 查询我的优惠卷
     * @param userId 用户id
     * @param useStatus 使用状态
     * @return
     */
    List<CouponHistoryRes> queryCouponListByUserId(Long userId, Integer useStatus);

    /**
     * todo 权益卡劵查询
     */

    /**
     * 获得卡劵
     * @param couponHistory 基本历史信息
     * @return
     */
    int addCouponHistory(CouponHistoryAdd couponHistory);

    /**
     * 使用卡劵
     * @param ids 历史卡劵id
     * @param userId 用户id
     * @param orderId 订单id
     * @return
     */
    int consumeCouponHistory(List<Long> ids, Long userId, String orderId);
}
