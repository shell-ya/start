package com.starnft.star.interfaces.controller.coupon;

import com.starnft.star.application.process.coupon.CouponCore;
import com.starnft.star.common.RopResponse;
import com.starnft.star.domain.coupon.model.res.CouponHistoryRes;
import com.starnft.star.interfaces.interceptor.UserContext;
import com.starnft.star.interfaces.interceptor.UserResolverInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/4 9:18 下午
 * @description： TODO
 */
@Api(tags = "卡劵接口「CouponController」")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/coupon")
public class CouponController {

    final CouponCore couponCore;

    @ApiOperation("我的优惠卷")
    @GetMapping("/my/list")
    public RopResponse<List<CouponHistoryRes>> list(@RequestParam("useStatus") Integer useStatus) {
        return RopResponse.success(couponCore.queryCouponListByUserId(UserContext.getUserId().getUserId(), useStatus));
    }


}
