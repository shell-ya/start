package com.starnft.star.domain.coupon.repository;

import com.starnft.star.domain.coupon.model.res.MyCouponRes;

import java.util.List;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/4 8:05 上午
 * @description： TODO
 */
public interface ICouponRepository {

    /**
     * 查询我的优惠卷
     * @param userId 用户id
     * @param useStatus 使用状态
     * @return
     */
    List<MyCouponRes> queryMyCouponList(Long userId, Integer useStatus);
}
