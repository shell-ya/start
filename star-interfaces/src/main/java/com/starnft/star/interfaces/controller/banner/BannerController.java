package com.starnft.star.interfaces.controller.banner;

import com.starnft.star.common.RopResponse;
import com.starnft.star.domain.banner.model.req.BannerReq;
import com.starnft.star.domain.banner.model.vo.BannerVo;
import com.starnft.star.domain.banner.service.IBannerService;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "轮播图相关接口「CentralController」")
@RequestMapping("/banner")
public class BannerController {

    @Resource
    private IBannerService bannerService;

    @ApiOperation("获取展示的轮播图")
    @GetMapping(value = "/show")
    @TokenIgnore
    public RopResponse<List<BannerVo>> show(@ApiParam("展示位置 1：头部  2：中间  3：底部") @RequestParam("type") String type, @ApiParam("轮播图数量") @RequestParam("size") int size) {
        return RopResponse.success(
                this.bannerService.queryBannerVo(BannerReq.builder().size(size).type(type).build())
        );
    }

}
