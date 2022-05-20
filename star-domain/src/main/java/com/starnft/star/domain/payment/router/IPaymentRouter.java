package com.starnft.star.domain.payment.router;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.handler.IPaymentHandler;
import com.starnft.star.domain.payment.router.model.PaymentRouterContext;

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
     * @description 根据条件获取对应的支付执行器
     * @date  2022/5/20
     * @param context
     * @return IPaymentHandler
     */
    IPaymentHandler<?> payRoute(PaymentRouterContext context);

    /**
     * @author Ryan Z / haoran
     * @description  获取渠道对应所需参数类型 用于获取IPaymentHandler 的第二个参数
     * @date  2022/5/20
     * @param payChannel
     * @return  Class 渠道对应所需参数类型
     */
    Class<?> obtainChannelRequest(StarConstants.PayChannel payChannel);



}
