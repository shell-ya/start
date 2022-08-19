package com.starnft.star.interfaces.controller.dingchain;

import com.starnft.star.domain.number.model.vo.NumberDingVO;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
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

    @RequestMapping("panel")
    @TokenIgnore
    public List<NumberDingVO> panel(){
        return iNumberService.getNumberDingList();
    }

    @RequestMapping("bulletin")
    @TokenIgnore
    public List<Map<String,Object>> bulletin(){
        Map<@Nullable String, @Nullable Object> resultMap = Maps.newHashMap();
        resultMap.put("title","40万实物邀新");
        resultMap.put("image","https://7463-tcb-jsq5g2gn216911-8dtv73c1de35f-1312710945.tcb.qcloud.la/img/76771660875671_.pic.jpg");
        resultMap.put("link","https://mp.weixin.qq.com/s/Zw1rxIi5z9DjieYZUUw7OA");
        ArrayList<Map<@Nullable String, @Nullable Object>> result = Lists.newArrayList();
        result.add(resultMap);
        return result;
    }

//    @RequestMapping("price")
//    @TokenIgnore
//    public Boolean price(@RequestParam("price") BigDecimal price){
//        return iNumberService.managePrice(price);
//    }
}
