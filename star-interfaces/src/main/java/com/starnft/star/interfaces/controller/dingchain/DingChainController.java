package com.starnft.star.interfaces.controller.dingchain;

import com.starnft.star.domain.number.model.vo.NumberDingVO;
import com.starnft.star.domain.number.serivce.INumberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
@Api(tags = "图像验证码相关接口「CaptchaController」")
@RequestMapping("/ding")
@RequiredArgsConstructor
@Slf4j
public class DingChainController {
    @Resource
    private  final INumberService iNumberService;
    @RequestMapping("panel")
    public List<NumberDingVO> panel(){
        return iNumberService.getNumberDingList();
    }

    @RequestMapping("bulletin")
    public List<NumberDingVO> bulletin(){
        return iNumberService.getNumberDingList();
    }

//    @RequestMapping("price")
//    @TokenIgnore
//    public Boolean price(@RequestParam("price") BigDecimal price){
//        return iNumberService.managePrice(price);
//    }
}
