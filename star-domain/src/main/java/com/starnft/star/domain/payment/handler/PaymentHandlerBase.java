package com.starnft.star.domain.payment.handler;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.config.PayConf;
import com.starnft.star.domain.payment.model.PaymentRes;
import com.starnft.star.domain.support.process.IInteract;
import com.starnft.star.domain.support.process.ProcessInteractionHolder;
import com.starnft.star.domain.support.process.context.ConnContext;

import java.util.Map;
import java.util.function.Supplier;

public abstract class PaymentHandlerBase<T>
        extends PayConf implements IPaymentHandler<T>, ProcessInteractionHolder {


    @Override
    public <R> PaymentRes pay(T payReq, Class<R> actualReqClazz, StarConstants.PayChannel payChannel) {

        //根据参数获取对应支付请求 payChannel获取对应支付配置
        R req = processParams(payReq, payChannel);
        //校验配置信息和参数信息
        verifyLegality(req);
        //获取协议调用器
        IInteract iInteract = obtainProcessInteraction(StarConstants.ProcessType.valueOf(getProtocol()));
        //获取支付调用结果
        String res = iInteract.interact(createConnContext(req), fillHeader());
        //返回结果
        return processPay(res);
    }

    protected abstract <R> R processParams(T payReq, StarConstants.PayChannel config);

    protected abstract <R> void verifyLegality(R req);

    protected abstract <R> ConnContext createConnContext(R req);

    protected abstract Supplier<Map<?, ?>> fillHeader();

    protected abstract PaymentRes processPay(String res);


}
