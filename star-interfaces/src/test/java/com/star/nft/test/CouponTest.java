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
        add.setCouponId("CK879278014611491840");
        add.setGetType(CouponGetType.ACTIVITY.getType());
        add.setUserId(657119310l);
        couponCore.addCouponHistory(add);

        CouponHistoryAdd add2 = new CouponHistoryAdd();
        add2.setCouponId("CK879278014611491840");
        add2.setGetType(CouponGetType.ACTIVITY.getType());
        add2.setUserId(657119310l);
        couponCore.addCouponHistory(add2);


        CouponHistoryAdd add3 = new CouponHistoryAdd();
        add3.setCouponId("CK879278014611491840");
        add3.setGetType(CouponGetType.ACTIVITY.getType());
        add3.setUserId(657119310l);
        couponCore.addCouponHistory(add3);

        CouponHistoryAdd add4 = new CouponHistoryAdd();
        add4.setCouponId("CK879278014611491840");
        add4.setGetType(CouponGetType.ACTIVITY.getType());
        add4.setUserId(657119310l);
        couponCore.addCouponHistory(add4);

    }

}
