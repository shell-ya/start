package com.starnft.star.domain.coupon.service.impl;

import com.starnft.star.domain.compose.service.IComposeService;
import com.starnft.star.domain.coupon.model.res.MyCouponRes;
import com.starnft.star.domain.coupon.repository.ICouponRepository;
import com.starnft.star.domain.coupon.service.ICouponService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/4 1:31 上午
 * @description： TODO
 */
@Service
public class CouponServiceImpl implements ICouponService {

    @Resource
    private ICouponRepository couponRepository;

    @Override
    public List<MyCouponRes> queryMyCouponList(Long userId, Integer useStatus) {
        List<MyCouponRes> myCouponRes = couponRepository.queryMyCouponList(userId, useStatus);
        return myCouponRes;
    }
}
