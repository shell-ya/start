package com.starnft.star.interfaces.controller.trans;

import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.domain.notify.service.PayNotifyService;
import com.starnft.star.domain.payment.model.res.NotifyRes;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/trans")
public class NotifyController {
    @Resource
    PayNotifyService payNotifyService;
    @Resource
    IMessageSender messageSender;
    @ResponseBody
    @TokenIgnore
    @RequestMapping("/notify/sand")
    public String notifyPay( HttpServletRequest request, HttpServletResponse response) {
        NotifyRes transform = payNotifyService.transform("sand", request, response);
        log.info("########################");
        log.info("{}回调了");
        log.info("########################");
        messageSender.asyncSend(transform.getTopic(), Optional.of(transform.getPayCheckRes()), null, null);
        return "success";

    }
}
