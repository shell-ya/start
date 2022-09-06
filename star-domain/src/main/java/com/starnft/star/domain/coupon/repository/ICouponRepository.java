package com.starnft.star.domain.coupon.repository;

import com.starnft.star.domain.coupon.model.dto.CouponHistoryAdd;
import com.starnft.star.domain.coupon.model.dto.CouponHistoryUpdate;
import com.starnft.star.domain.coupon.model.req.CouponHistoryReq;
import com.starnft.star.domain.coupon.model.res.CouponHistoryInfoRes;
import com.starnft.star.domain.coupon.model.res.CouponInfoRes;
import com.starnft.star.domain.coupon.model.res.CouponHistoryRes;

import java.util.List;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/4 8:05 上午
 * @description： TODO
 */
public interface ICouponRepository {

    /**
     * 根据 用户id 查询优惠卷
     * @param req
     * @return
     */
    List<CouponHistoryRes> queryCouponListByCouponHistory(CouponHistoryReq req);

    /**
     * 根据卡劵id查询卡劵基本信息
     * @param couponId 卡劵id
     * @return 卡劵基本信息
     */
    CouponInfoRes queryCouponInfoById(String couponId);

    /**
     * 根据卡劵id列表查询卡劵基本信息列表
     * @param couponIdList 卡劵id列表
     * @return 卡劵基本信息列表
     */
    List<CouponInfoRes> queryCouponInfoByIdList(List<String> couponIdList);

    /**
     * 根据历史卡劵id查询基本信息
     * @param id 历史卡劵id
     * @return
     */
    CouponHistoryInfoRes queryCouponHistoryInfoById(Long id);

    /**
     * 新增卡劵历史信息
     * @param couponHistory 基本历史信息
     * @return
     */
    int addCouponHistory(CouponHistoryAdd couponHistory);

    /**
     * 修改卡劵历史信息
     * @param couponHistory 基本历史信息
     * @return
     */
    int updateCouponHistory(CouponHistoryUpdate couponHistory);
}
