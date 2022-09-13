package com.starnft.star.domain.coupon.service.impl;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.starnft.star.common.constant.YesOrNoStatusEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.coupon.model.dto.CouponHistoryAdd;
import com.starnft.star.domain.coupon.model.dto.CouponHistoryUpdate;
import com.starnft.star.domain.coupon.model.dto.CouponInfoUpdate;
import com.starnft.star.domain.coupon.model.req.CouponHistoryReq;
import com.starnft.star.domain.coupon.model.res.CouponHistoryInfoRes;
import com.starnft.star.domain.coupon.model.res.CouponHistoryRes;
import com.starnft.star.domain.coupon.model.res.CouponInfoRes;
import com.starnft.star.domain.coupon.repository.ICouponRepository;
import com.starnft.star.domain.coupon.service.ICouponService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        List<CouponHistoryRes> couponHistoryRes = couponRepository.queryCouponListByCouponHistory(req);
        if (CollectionUtils.isNotEmpty(couponHistoryRes)){
            List<CouponHistoryRes> list = Lists.newArrayList();
            Map<String, CouponHistoryRes> map = Maps.newHashMap();
            //分组
            Map<String, Long>  couponHistoryMap = couponHistoryRes.stream().collect(Collectors.groupingBy(CouponHistoryRes::getCouponId, Collectors.counting()));
            for (CouponHistoryRes couponHistoryRe : couponHistoryRes) {
                 if (!map.containsKey(couponHistoryRe.getCouponId())){
                     map.put(couponHistoryRe.getCouponId(), couponHistoryRe);
                     couponHistoryRe.setCount(couponHistoryMap.get(couponHistoryRe.getCouponId()));
                     list.add(couponHistoryRe);
                 }
            }
            return list;
        }
        return Lists.newArrayList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addCouponHistory(CouponHistoryAdd couponHistory) {
        CouponInfoRes couponInfoRes = couponRepository.queryCouponInfoById(couponHistory.getCouponId());
        if (Objects.isNull(couponInfoRes)){
            throw new StarException(StarError.PARAETER_UNSUPPORTED,"该卡劵不存在");
        }
        //发行数量更新
        CouponInfoUpdate couponInfoUpdate = new CouponInfoUpdate();
        couponInfoUpdate.setCouponId(couponHistory.getCouponId());
        couponInfoUpdate.setReceiveCount(couponInfoRes.getReceiveCount() + 1);
        couponRepository.updateCouponInfo(couponInfoUpdate);
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
        couponHistory.setUseStatus(YesOrNoStatusEnum.YES.getCode());
        couponHistory.setUseTime(DateUtil.date());
        couponHistory.setOrderId(couponHistory.getOrderId());
        return couponRepository.updateCouponHistory(couponHistory);
    }


}
