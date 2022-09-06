package com.starnft.star.domain.coupon.service;

import com.starnft.star.domain.coupon.model.dto.CouponHistoryAdd;
import com.starnft.star.domain.coupon.model.dto.CouponHistoryUpdate;
import com.starnft.star.domain.coupon.model.res.CouponHistoryRes;

import java.util.List;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/3 2:38 下午
 * @description： TODO
 */
public interface ICouponService {

    /**
     * 查询我的优惠卷
     * @param userId 用户id
     * @param useStatus 使用状态
     * @return
     */
    List<CouponHistoryRes> queryCouponListByUserId(Long userId, Integer useStatus);

    /**
     * 获取卡劵
     * @param couponHistory 基本历史信息
     * @return
     */
    int addCouponHistory(CouponHistoryAdd couponHistory);

    /**
     * 减除卡劵/使用卡劵
     * @param couponHistory 基本历史信息
     * @return
     */
    int subtractCouponHistory(CouponHistoryUpdate couponHistory);
}
