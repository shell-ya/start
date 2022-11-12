package com.starnft.star.interfaces.controller.mall;

import cn.hutool.json.JSONUtil;
import com.starnft.star.application.process.limit.ICurrentLimiter;
import com.starnft.star.application.process.number.req.MarketOrderReq;
import com.starnft.star.application.process.order.IOrderProcessor;
import com.starnft.star.application.process.order.model.req.OrderCancelReq;
import com.starnft.star.application.process.order.model.req.OrderGrabReq;
import com.starnft.star.application.process.order.model.req.OrderPayReq;
import com.starnft.star.application.process.order.model.res.OrderGrabRes;
import com.starnft.star.application.process.order.model.res.OrderGrabStatus;
import com.starnft.star.application.process.order.model.res.OrderPayDetailRes;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.order.model.req.OrderListReq;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.order.service.model.res.OrderPlaceRes;
import com.starnft.star.interfaces.controller.trans.redis.RedisDistributedLock;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import com.starnft.star.interfaces.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * 订单相关接口
 */
@Api(tags = "订单接口「OrderController」")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    final IOrderProcessor orderProcessor;

    final IOrderService orderService;

    final ICurrentLimiter currentLimiter;

    final RedisDistributedLock redisDistributedLock;

    @ApiOperation("下单")
    @PostMapping("/grab")
    public RopResponse<OrderGrabRes> grab(@Validated @RequestBody OrderGrabReq req) {
        if (!currentLimiter.tryAcquire()) {
            throw new StarException(StarError.REQUEST_OVERFLOW_ERROR);
        }
        req.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(this.orderProcessor.orderGrab(req));
    }

    @ApiOperation("创建订单")
    @PostMapping("/createOrder")
    public RopResponse<OrderGrabRes> createOrder(@Validated @RequestBody OrderGrabReq req) {
        log.info("[createOrder]下单参数：{}", JSONUtil.toJsonStr(req));
        if (!currentLimiter.tryAcquire()) {
            throw new StarException(StarError.REQUEST_OVERFLOW_ERROR);
        }
        String lockKey = "lockKey_" + req.getThemeId();
        String lockId = null;
        try {
            lockId = redisDistributedLock.lock(lockKey, 10, 10, TimeUnit.SECONDS);
            req.setUserId(UserContext.getUserId().getUserId());
            OrderGrabRes result = this.orderProcessor.createOrder(req);
            return RopResponse.success(result);
        } catch (StarException e) {
            log.error("StarException,msg:{}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("系统繁忙，请稍后操作：{}", e.getMessage(), e);
            throw new StarException(StarError.REQUEST_OVERFLOW_ERROR);
        } finally {
            redisDistributedLock.releaseLock(lockKey, lockId);
        }
    }

    @ApiOperation("查询订单列表")
    @PostMapping("/list")
    public RopResponse<ResponsePageResult<OrderListRes>> list(@RequestBody OrderListReq req) {
        req.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(this.orderService.orderList(req));
    }

    @ApiOperation("查询订单详情")
    @PostMapping("/details")
    public RopResponse<OrderListRes> detail(@RequestBody OrderListReq req) {
        req.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(this.orderProcessor.orderDetails(req));
    }

    @ApiOperation("查询下单状态")
    @PostMapping("/status")
    public RopResponse<OrderGrabStatus> orderedStatus(@RequestBody OrderGrabReq req) {
        req.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(this.orderProcessor.obtainSecKIllStatus(req));
    }

    @ApiOperation("查询抢单成功秒杀订单")
    @PostMapping("/killed/details")
    public RopResponse<OrderListRes> orderedDetails(@RequestBody OrderGrabReq req) {
        req.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(this.orderProcessor.obtainSecKillOrder(req));
    }

    @ApiOperation("秒杀订单支付")
    @PostMapping("/killed/pay")
    public RopResponse<OrderPayDetailRes> orderPay(@RequestBody OrderPayReq req) {
        req.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(this.orderProcessor.orderPay(req));
    }

    @ApiOperation("市场订单云账户支付")
    @PostMapping("/marketOrder/cloudAccountPay")
    public RopResponse<OrderPayDetailRes> cloudAccountPay(@RequestBody OrderPayReq req) {
        req.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(this.orderProcessor.cloudAccountPay(req));
    }


    @ApiOperation("取消秒杀订单")
    @PostMapping("/killed/cancel")
    public RopResponse<OrderPlaceRes> killedCancel(@RequestBody OrderCancelReq req) {
        req.setUid(UserContext.getUserId().getUserId());
        return RopResponse.success(this.orderProcessor.cancelSecOrder(req));
    }

    @ApiOperation("云钱包支付订单详情")
    @TokenIgnore
    @PostMapping("/payed/{orderSn}")
    public RopResponse<OrderListRes> killedCancel(@PathVariable String orderSn) {
        return RopResponse.success(this.orderProcessor.payDetails(orderSn));
    }

    @PostMapping("/market/grab")
    @ApiOperation("市场下单")
    public RopResponse<OrderListRes> order(@RequestBody MarketOrderReq request) {
        if (!currentLimiter.tryAcquire()) {
            throw new StarException(StarError.REQUEST_OVERFLOW_ERROR);
        }
        request.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(this.orderProcessor.marketOrder(request));
    }

    @PostMapping("/orderData/clear")
    @TokenIgnore
    @ApiOperation("清理异常数据")
    public RopResponse<Boolean> orderDataClear(@RequestParam String themeId, @RequestParam String keySecret) {
        return RopResponse.success(this.orderProcessor.dataCheck(Long.parseLong(themeId), keySecret));
    }

    @PostMapping("/priority/times")
    @TokenIgnore
    @ApiOperation("用户优先购次数")
    public RopResponse<Integer> priorityTimes(@RequestParam String userId) {
        return RopResponse.success(this.orderProcessor.priorityTimes(Long.parseLong(userId)));
    }

}
