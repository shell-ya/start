package com.starnft.star.application.process.coupon.impl;

import com.starnft.star.application.process.coupon.CouponCore;
import com.starnft.star.domain.coupon.model.dto.CouponHistoryAdd;
import com.starnft.star.domain.coupon.model.dto.CouponHistoryUpdate;
import com.starnft.star.domain.coupon.model.res.CouponHistoryRes;
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
    public List<CouponHistoryRes> queryCouponListByUserId(Long userId, Integer useStatus) {
        return couponService.queryCouponListByUserId(userId, useStatus);
    }

    @Override
    public int addCouponHistory(CouponHistoryAdd couponHistory) {
        return couponService.addCouponHistory(couponHistory);
    }

    @Override
    public int consumeCouponHistory(List<Long> ids, Long userId, String orderId) {
        CouponHistoryUpdate update = new CouponHistoryUpdate();
        update.setOrderId(orderId);
        update.setIdList(ids);
        update.setUserId(userId);
        return couponService.subtractCouponHistory(update);
    }
}
