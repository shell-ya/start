package com.starnft.star.domain.coupon.service.impl;

import cn.hutool.core.date.DateUtil;
import com.starnft.star.common.enums.CouponUseStatus;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.coupon.model.dto.CouponHistoryAdd;
import com.starnft.star.domain.coupon.model.dto.CouponHistoryUpdate;
import com.starnft.star.domain.coupon.model.req.CouponHistoryReq;
import com.starnft.star.domain.coupon.model.res.CouponHistoryInfoRes;
import com.starnft.star.domain.coupon.model.res.CouponHistoryRes;
import com.starnft.star.domain.coupon.model.res.CouponInfoRes;
import com.starnft.star.domain.coupon.repository.ICouponRepository;
import com.starnft.star.domain.coupon.service.ICouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/4 1:31 上午
 * @description： TODO
 */
@Service
@Slf4j
public class CouponServiceImpl implements ICouponService {

    @Resource
    private ICouponRepository couponRepository;

    @Override
    public List<CouponHistoryRes> queryCouponListByUserId(Long userId, Integer useStatus) {
        CouponHistoryReq req = new CouponHistoryReq();
        req.setUserId(userId);
        req.setUseStatus(useStatus);
        return couponRepository.queryCouponListByCouponHistory(req);
    }

    @Override
    public int addCouponHistory(CouponHistoryAdd couponHistory) {
        CouponInfoRes couponInfoRes = couponRepository.queryCouponInfoById(couponHistory.getCouponId());
        if (Objects.isNull(couponInfoRes)){
            throw new StarException(StarError.PARAETER_UNSUPPORTED,"该卡劵不存在");
        }
        return couponRepository.addCouponHistory(couponHistory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int subtractCouponHistory(CouponHistoryUpdate couponHistory) {
        for (Long id : couponHistory.getIdList()) {
            //校验卡劵是否属于该用户
            CouponHistoryInfoRes couponHistoryInfoRes = couponRepository.queryCouponHistoryInfoById(id);
            if (Objects.isNull(couponHistoryInfoRes) || Objects.equals(couponHistory.getUserId(), couponHistoryInfoRes.getUserId())){
                throw new StarException(StarError.PARAETER_UNSUPPORTED, "该卡劵不存在或不属于该用户");
            }
        }
        couponHistory.setUseStatus(CouponUseStatus.Airdrop.getType());
        couponHistory.setUseTime(DateUtil.date());
        couponHistory.setOrderId(couponHistory.getOrderId());
        return couponRepository.updateCouponHistory(couponHistory);
    }


}
