package com.starnft.star.domain.payment.router;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.handler.IPaymentHandler;

import java.util.List;


/**
 * @author Ryan Z / haoran
 * @description 支付路由
 * @date  2022/5/20
 */
public interface IPaymentRouter {


    /**
     * @author Ryan Z / haoran
     * @description 获取支持的支付渠道
     * @date  2022/5/20
     * @return 目前支持的渠道
     */
    List<Enum<StarConstants.PayChannel>> obtainSupported();

    /**
     * @author Ryan Z / haoran
     * @description 根据支付渠道编码获取对应的支付执行器
     * @date  2022/5/20
     * @param payChannel 渠道
     * @return IPaymentHandler
     */
    IPaymentHandler payRoute(String payChannel);




}
