package com.starnft.star.application.process.coupon;

import com.starnft.star.domain.coupon.model.res.MyCouponRes;

import java.util.List;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/4 1:25 上午
 * @description： TODO
 */
public interface CouponCore {

    List<MyCouponRes> queryMyCouponList(Long userId, Integer useStatus);

    //todo 收银台卡劵权益

    ///todo 卡劵获得

    //todo 卡劵id查询卡劵基本信息
}
