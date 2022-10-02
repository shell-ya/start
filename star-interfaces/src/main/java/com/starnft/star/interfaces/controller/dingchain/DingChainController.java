package com.starnft.star.interfaces.controller.dingchain;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.starnft.star.domain.bulletin.IBulletinService;
import com.starnft.star.domain.bulletin.impl.BulletinServiceImpl;
import com.starnft.star.domain.number.model.vo.DingBulletinVo;
import com.starnft.star.domain.number.model.vo.NumberDingVO;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "图像验证码相关接口「CaptchaController」")
@RequestMapping("/ding")
@RequiredArgsConstructor
@Slf4j
public class DingChainController {
    @Resource
    private  final INumberService iNumberService;
    @Resource
    private IBulletinService bulletinService;

    @RequestMapping("panel")
    @TokenIgnore
    public List<NumberDingVO> panel(){
        return iNumberService.getNumberDingList();
    }

    @RequestMapping("bulletin")
    @TokenIgnore
    public List<DingBulletinVo> bulletin(){
        return bulletinService.queryDingBulletin();
    }

//    @RequestMapping("price")
//    @TokenIgnore
//    public Boolean price(@RequestParam("price") BigDecimal price){
//        return iNumberService.managePrice(price);
//    }
}
