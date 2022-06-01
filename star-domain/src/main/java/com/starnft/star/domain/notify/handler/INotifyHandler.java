package com.starnft.star.domain.notify.handler;

import com.starnft.star.common.enums.PlatformTypeEnum;

import javax.servlet.http.HttpServletRequest;

public interface INotifyHandler {
    String doNotify(HttpServletRequest request);

    //支付渠道
    PlatformTypeEnum getNotifyChannel();

}
