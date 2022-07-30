package com.starnft.star.interfaces.controller.mall;

import com.starnft.star.application.process.theme.ThemeCore;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.res.ThemeDetailRes;
import com.starnft.star.domain.theme.model.res.ThemeRes;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@Api(tags = "主题相关接口「ThemeController」")
@RequestMapping("/theme")
public class ThemeController {

    @Resource
    ThemeCore themeCore;

    @GetMapping("/{seriesId}")
    @ApiOperation("获取系列ID下所有主题")
    @TokenIgnore
    public RopResponse<List<ThemeRes>> getAllTheme(@PathVariable @ApiParam("系列id") Long seriesId) {
        return RopResponse.success(this.themeCore.queryThemesBySeriesId(seriesId));
    }

    @PostMapping("/page/{id}")
    @ApiOperation("获取系列ID下所有主题 - 分页")
    @TokenIgnore
    public RopResponse<ResponsePageResult<ThemeRes>> seriesTheme(@PathVariable @ApiParam("系列id") Long id, @RequestBody RequestPage requestPage) {
        return RopResponse.success(
                this.themeCore
                        .queryMainThemeInfo(ThemeReq.builder()
                                .page(requestPage.getPage())
                                .size(requestPage.getSize())
                                .seriesId(id)
                                .build())
        );
    }

    @PostMapping("/detail/{id}")
    @ApiOperation("主题详情")
    @TokenIgnore
    public RopResponse<ThemeDetailRes> seriesTheme(@PathVariable @ApiParam("主题id") Long id) {
        return RopResponse.success(this.themeCore.queryThemeDetail(id));
    }

    @PostMapping("/seckill/list")
    @ApiOperation("秒杀商品列表")
    @TokenIgnore
    public RopResponse<Set<SecKillGoods>> seriesTheme() {
        return RopResponse.success(this.themeCore.querySecKillThemes());
    }

}
