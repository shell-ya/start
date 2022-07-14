package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.vo.FrontVo;
import com.starnft.star.business.service.IMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Date 2022/6/10 1:15 AM
 * @Author ： shellya
 */
@RestController
@RequestMapping("/business/main")
public class MainController {

    @Resource
    private IMainService mainService;

    @PostMapping("/getFront")
    public FrontVo getFront(){
        return mainService.getFront();
    }
}
