package com.starnft.star.domain.notify.handler;

import com.starnft.star.common.enums.PlatformTypeEnum;
import com.starnft.star.domain.payment.model.res.NotifyRes;

import javax.servlet.http.HttpServletRequest;

public interface INotifyHandler {
    NotifyRes doNotify(HttpServletRequest request);

    //支付渠道
    PlatformTypeEnum getNotifyChannel();

}
