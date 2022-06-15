package com.starnft.star.interfaces.controller.mall;

import com.starnft.star.application.process.limit.ICurrentLimiter;
import com.starnft.star.application.process.order.IOrderProcessor;
import com.starnft.star.application.process.order.model.req.OrderGrabReq;
import com.starnft.star.application.process.order.model.res.OrderGrabRes;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.interfaces.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单相关接口
 */
@Api(tags = "订单接口「OrderController」")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/order")
public class OrderController {

    final IOrderProcessor orderProcessor;

    final ICurrentLimiter currentLimiter;

    @ApiOperation("下单")
    @PostMapping("/ordergrab")
    public RopResponse<OrderGrabRes> recharge(@Validated @RequestBody OrderGrabReq req) {
        if (!currentLimiter.tryAcquire()) {
            throw new StarException(StarError.REQUEST_OVERFLOW_ERROR);
        }
        req.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(this.orderProcessor.orderGrab(req));
    }
}
