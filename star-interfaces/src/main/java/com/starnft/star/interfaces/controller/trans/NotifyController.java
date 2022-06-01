package com.starnft.star.interfaces.controller.trans;

import com.starnft.star.application.mq.producer.RocketMQProducer;
import com.starnft.star.domain.notify.service.PayNotifyService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/notify")
public class NotifyController {
    @Resource
     PayNotifyService payNotifyService;

    @Resource
    RocketMQProducer rocketMQProducer;
    @RequestMapping("/notifyPay/{platform}")
     public String  notifyPay(@PathVariable String platform, HttpServletRequest request, HttpServletResponse response){
        String transform = payNotifyService.transform(platform, request, response);
        rocketMQProducer.asyncSend("xxxx", Optional.of(transform));
        return transform;
    }
}
