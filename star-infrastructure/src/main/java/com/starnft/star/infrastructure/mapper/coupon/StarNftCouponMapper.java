package com.starnft.star.infrastructure.mapper.coupon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.infrastructure.entity.coupon.StarNftCoupon;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/3 2:38 下午
 * @description： TODO
 */
@Mapper
public interface StarNftCouponMapper extends BaseMapper<StarNftCoupon> {

    List<StarNftCoupon> queryAllByHistory(Long userId, Integer useStatus);
}
