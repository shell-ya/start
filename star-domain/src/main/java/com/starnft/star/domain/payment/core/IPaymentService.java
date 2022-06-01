package com.starnft.star.domain.payment.core;


import com.starnft.star.domain.payment.model.req.PayCheckReq;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.payment.model.res.PaymentRes;

import java.util.List;

/**
 * 该接口相当于适配层 组装领域内的功能 只暴露api的内容 方便后续拆分服务
 */
public interface IPaymentService {

    List<String> obtainSupported();
    PaymentRes pay(PaymentRich payReq);
    PayCheckRes orderCheck(PayCheckReq payReq);
}
