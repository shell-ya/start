package com.starnft.star.interfaces.controller.dingchain;

import com.starnft.star.domain.bulletin.IBulletinService;
import com.starnft.star.domain.number.model.vo.DingBulletinVo;
import com.starnft.star.domain.number.model.vo.NumberDingVO;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "盯链对接相关接口「DingChainController」")
@RequestMapping("/ding")
@RequiredArgsConstructor
@Slf4j
public class DingChainController {
    @Resource
    private  final INumberService iNumberService;

    @Resource
    private IBulletinService bulletinService;

    /**
     * 价格
     * @return
     */
    @GetMapping("panel")
    @TokenIgnore
    public List<NumberDingVO> panel(){
        return iNumberService.getNumberDingList();
    }

    /**
     * 公告
     * @return
     */
    @GetMapping("bulletin")
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
