package com.starnft.star.interfaces.controller.banner;

import com.starnft.star.common.RopResponse;
import com.starnft.star.domain.banner.model.req.BannerReq;
import com.starnft.star.domain.banner.model.vo.BannerVo;
import com.starnft.star.domain.banner.service.IBannerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date 2022/5/9 2:49 PM
 * @Author ： shellya
 */
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Resource
    private IBannerService bannerService;

//    @ApiOperation("轮播图展示")
    @GetMapping(value = "/show")
    public RopResponse<List<BannerVo>> show(@RequestParam("type") String type ,@RequestParam("size") int size){
        return RopResponse.success(
            bannerService.queryBannerVo(BannerReq.builder().size(size).type(type).build())
        );
    }

}
