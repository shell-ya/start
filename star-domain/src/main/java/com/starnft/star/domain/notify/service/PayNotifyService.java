package com.starnft.star.domain.notify.service;

import com.starnft.star.domain.payment.model.res.NotifyRes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PayNotifyService {
    NotifyRes transform(String platform, HttpServletRequest request, HttpServletResponse response);
}
