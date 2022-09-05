package com.starnft.star.domain.coupon.service;

import com.starnft.star.domain.coupon.model.res.MyCouponRes;

import java.util.List;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/3 2:38 下午
 * @description： TODO
 */
public interface ICouponService {

    /**
     * 查询我的优惠卷
     * @return
     */
    List<MyCouponRes> queryMyCouponList(Long userId, Integer useStatus);
}
