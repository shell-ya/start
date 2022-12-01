package com.starnft.star.interfaces.controller.trans;

import com.starnft.star.application.process.task.activity.ActivitiesTask;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 新杉德支付回调服务
 */
@RestController
@Api(tags = "加载服务｜LoadController")
@Slf4j
public class LoadController {

    @Autowired
    private ActivitiesTask activitiesTask;

    @TokenIgnore
    @RequestMapping(path = "/loadData", method = {RequestMethod.GET})
    public String loadData() {

        log.info("[loadData]收到加载通知.....");
        activitiesTask.loadActivities();
        log.info("[loadData]加载数据完成.....");

        return "success";
    }

}
