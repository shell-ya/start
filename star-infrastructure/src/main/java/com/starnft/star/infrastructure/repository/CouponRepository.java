package com.starnft.star.infrastructure.repository;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.starnft.star.common.constant.YesOrNoStatusEnum;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.coupon.model.dto.CouponHistoryAdd;
import com.starnft.star.domain.coupon.model.dto.CouponHistoryUpdate;
import com.starnft.star.domain.coupon.model.dto.CouponInfoUpdate;
import com.starnft.star.domain.coupon.model.req.CouponHistoryReq;
import com.starnft.star.domain.coupon.model.res.CouponHistoryInfoRes;
import com.starnft.star.domain.coupon.model.res.CouponHistoryRes;
import com.starnft.star.domain.coupon.model.res.CouponInfoRes;
import com.starnft.star.domain.coupon.repository.ICouponRepository;
import com.starnft.star.infrastructure.entity.coupon.StarNftCoupon;
import com.starnft.star.infrastructure.entity.coupon.StarNftCouponHistory;
import com.starnft.star.infrastructure.mapper.coupon.StarNftCouponHistoryMapper;
import com.starnft.star.infrastructure.mapper.coupon.StarNftCouponMapper;
import com.starnft.star.infrastructure.mapper.coupon.StarNftCouponSeriesRelationMapper;
import com.starnft.star.infrastructure.mapper.coupon.StarNftCouponThemeRelationMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

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
    public List<CouponHistoryRes> queryCouponListByCouponHistory(CouponHistoryReq req) {
        return starNftCouponMapper.queryAllByHistory(req);
    }

    @Override
    public CouponInfoRes queryCouponInfoById(String couponId) {
        LambdaQueryWrapper<StarNftCoupon> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StarNftCoupon::getCouponId, couponId);
        StarNftCoupon starNftCoupon = starNftCouponMapper.selectOne(queryWrapper);
        return BeanColverUtil.colver(starNftCoupon, CouponInfoRes.class);
    }

    @Override
    public List<CouponInfoRes> queryCouponInfoByIdList(List<String> couponIdList) {
        LambdaQueryWrapper<StarNftCoupon> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(StarNftCoupon::getCouponId, couponIdList);
        List<StarNftCoupon> starNftCoupons = starNftCouponMapper.selectList(queryWrapper);
        return BeanColverUtil.colverList(starNftCoupons, CouponInfoRes.class);
    }

    @Override
    public CouponHistoryInfoRes queryCouponHistoryInfoById(Long id) {
        LambdaQueryWrapper<StarNftCouponHistory> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StarNftCouponHistory::getId, id);
        return BeanColverUtil.colver(starNftCouponHistoryMapper.selectOne(queryWrapper), CouponHistoryInfoRes.class);
    }

    @Override
    public int addCouponHistory(CouponHistoryAdd couponHistory) {
        StarNftCouponHistory starNftCouponHistory = new StarNftCouponHistory();
        starNftCouponHistory.setChannelId(couponHistory.getChannelId());
        starNftCouponHistory.setCouponId(couponHistory.getCouponId());
        starNftCouponHistory.setUserId(couponHistory.getUserId());
        starNftCouponHistory.setGetType(couponHistory.getGetType());
        starNftCouponHistory.setCreatedAt(DateUtil.date());
        starNftCouponHistory.setModifiedAt(DateUtil.date());
        starNftCouponHistory.setIsDeleted(Boolean.FALSE);
        starNftCouponHistory.setCreatedBy(couponHistory.getUserId());
        starNftCouponHistory.setModifiedBy(couponHistory.getUserId());
        starNftCouponHistory.setUseStatus(YesOrNoStatusEnum.NO.getCode());
        return starNftCouponHistoryMapper.insert(starNftCouponHistory);
    }

    @Override
    public int updateCouponHistory(CouponHistoryUpdate couponHistory) {
        LambdaUpdateWrapper<StarNftCouponHistory> queryWrapper = new LambdaUpdateWrapper();
        queryWrapper.in(StarNftCouponHistory::getId, couponHistory.getIdList());
        StarNftCouponHistory starNftCouponHistory = new StarNftCouponHistory();
        starNftCouponHistory.setOrderId(couponHistory.getOrderId());
        starNftCouponHistory.setUseStatus(couponHistory.getUseStatus());
        starNftCouponHistory.setUseTime(couponHistory.getUseTime());
        starNftCouponHistory.setModifiedAt(DateUtil.date());
        starNftCouponHistory.setModifiedBy(couponHistory.getUserId());
        return starNftCouponHistoryMapper.update(starNftCouponHistory, queryWrapper);
    }

    @Override
    public int updateCouponInfo(CouponInfoUpdate couponInfoUpdate) {
        StarNftCoupon coupon = BeanColverUtil.colver(couponInfoUpdate, StarNftCoupon.class);
        LambdaUpdateWrapper<StarNftCoupon> queryWrapper = new LambdaUpdateWrapper();
        queryWrapper.in(StarNftCoupon::getCouponId, couponInfoUpdate.getCouponId());
        coupon.setModifiedAt(DateUtil.date());
        return starNftCouponMapper.update(coupon, queryWrapper);
    }
}
