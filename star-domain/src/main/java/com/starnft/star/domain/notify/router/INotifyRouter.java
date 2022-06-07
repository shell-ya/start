package com.starnft.star.domain.notify.router;

import com.starnft.star.domain.payment.model.res.NotifyRes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface INotifyRouter {

    NotifyRes doDistribute(String sign, HttpServletRequest request, HttpServletResponse response);
}
