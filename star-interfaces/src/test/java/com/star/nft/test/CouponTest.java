package com.star.nft.test;

import com.starnft.star.application.process.coupon.CouponCore;
import com.starnft.star.application.process.coupon.impl.CouponCoreImpl;
import com.starnft.star.common.enums.CouponGetType;
import com.starnft.star.domain.coupon.model.dto.CouponHistoryAdd;
import com.starnft.star.interfaces.StarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/9 10:37 上午
 * @description： TODO
 */
@SpringBootTest(classes = {StarApplication.class})
public class CouponTest {

    @Resource
    private CouponCoreImpl couponCore;

    @Test
    public void testAddCoupon(){
        CouponHistoryAdd add = new CouponHistoryAdd();
        add.setCouponId("DR999273003453906944");
        add.setGetType(CouponGetType.ACTIVITY.getType());
        add.setUserId(657119310l);
        couponCore.addCouponHistory(add);
    }

}
