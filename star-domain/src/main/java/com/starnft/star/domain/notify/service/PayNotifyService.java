package com.starnft.star.domain.notify.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PayNotifyService {
    String transform(String platform, HttpServletRequest request, HttpServletResponse response);
}
