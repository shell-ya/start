package com.starnft.star.domain.notify.router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface INotifyRouter {

    String doDistribute(String sign, HttpServletRequest request, HttpServletResponse response);
}
