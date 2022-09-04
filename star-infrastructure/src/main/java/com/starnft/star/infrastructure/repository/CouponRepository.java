package com.starnft.star.infrastructure.repository;

import com.starnft.star.domain.coupon.model.res.MyCouponRes;
import com.starnft.star.domain.coupon.repository.ICouponRepository;
import com.starnft.star.infrastructure.entity.coupon.StarNftCoupon;
import com.starnft.star.infrastructure.mapper.coupon.StarNftCouponHistoryMapper;
import com.starnft.star.infrastructure.mapper.coupon.StarNftCouponMapper;
import com.starnft.star.infrastructure.mapper.coupon.StarNftCouponSeriesRelationMapper;
import com.starnft.star.infrastructure.mapper.coupon.StarNftCouponThemeRelationMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/4 8:08 上午
 * @description： TODO
 */
@Repository
public class CouponRepository implements ICouponRepository {

    @Resource
    private StarNftCouponHistoryMapper starNftCouponHistoryMapper;
    @Resource
    private StarNftCouponMapper starNftCouponMapper;
    @Resource
    private StarNftCouponSeriesRelationMapper starNftCouponSeriesRelationMapper;
    @Resource
    private StarNftCouponThemeRelationMapper starNftCouponThemeRelationMapper;

    @Override
    public List<MyCouponRes> queryMyCouponList(Long userId, Integer useStatus) {
        List<StarNftCoupon> starNftCoupons = starNftCouponMapper.queryAllByHistory(userId, useStatus);
        if (CollectionUtils.isNotEmpty(starNftCoupons)){
           return starNftCoupons.stream().map(val -> convertMyCouponRes(val)).collect(Collectors.toList());
        }
        return null;
    }

    private MyCouponRes convertMyCouponRes(StarNftCoupon starNftCoupon){
        return new MyCouponRes()
                .setComposeName(starNftCoupon.getName())
                .setCount(starNftCoupon.getCount())
                .setEndAt(starNftCoupon.getEndTime())
                .setImages(starNftCoupon.getImage())
                .setType(starNftCoupon.getType());
    }
}
