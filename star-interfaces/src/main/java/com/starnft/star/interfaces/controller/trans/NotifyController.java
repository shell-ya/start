package com.starnft.star.interfaces.controller.trans;

import com.starnft.star.application.process.notify.impl.NotifyCore;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@RequestMapping("/trans")
public class NotifyController {
    @Resource
    NotifyCore notifyCore;
    @ResponseBody
    @TokenIgnore
    @RequestMapping("/notify/{platform}")
    public String notifyPay(@PathVariable("platform")String platform,HttpServletRequest request, HttpServletResponse response) {
            log.info("############回调进入了#################");
            notifyCore.handler(platform, request, response);
            return "success";
    }
}
