package com.starnft.star.application.process.coupon.impl;

import com.starnft.star.application.process.coupon.CouponCore;
import com.starnft.star.domain.coupon.model.res.MyCouponRes;
import com.starnft.star.domain.coupon.service.ICouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/4 9:14 下午
 * @description： TODO
 */
@Slf4j
@Service
public class CouponCoreImpl implements CouponCore {

    @Resource
    private ICouponService couponService;

    @Override
    public List<MyCouponRes> queryMyCouponList(Long userId, Integer useStatus) {
        return couponService.queryMyCouponList(userId, useStatus);
    }
}
