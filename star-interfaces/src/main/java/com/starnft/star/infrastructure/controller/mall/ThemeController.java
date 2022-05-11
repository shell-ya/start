package com.starnft.star.infrastructure.controller.mall;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.RequestPage;
import com.starnft.star.domain.numbers.model.OrderByEnum;
import com.starnft.star.domain.numbers.model.dto.NumberDTO;
import com.starnft.star.domain.numbers.model.req.NumberReq;
import com.starnft.star.domain.numbers.model.serivce.NumberService;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.service.ThemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "主题相关接口「ThemeController」")
@RequestMapping("/theme")
public class ThemeController {
    @Resource
    ThemeService themeService;
    @Resource
    NumberService numberService;
    @PostMapping("/seriesTheme/{id}")
    @ApiOperation("依据系列ID查找主题")
    public RopResponse seriesTheme(@PathVariable @ApiParam("系列id") Long  id, @RequestBody RequestPage requestPage){
        return  RopResponse.success(
                themeService
                        .queryMainThemeInfo(ThemeReq.builder()
                                .page(requestPage.getPage())
                                .size(requestPage.getSize())
                                .seriesId(id)
                                .build())
        );
    }

    @PostMapping("/theme/detail/{id}")
    @ApiOperation("主题详情")
    public RopResponse seriesTheme(@PathVariable @ApiParam("主题id") Long  id){
        return  RopResponse.success( themeService.queryThemeDetail(id) );
    }

    @PostMapping("/theme/detail/numbers/{id}")
    @ApiOperation("主题详情")
    public RopResponse seriesThemeNumbers(@PathVariable @ApiParam("主题id") Long  id,@RequestBody RequestConditionPage<NumberDTO> page){

         return  RopResponse.success(numberService
                 .queryThemeNumber( NumberReq.builder()
                         .page(page.getPage())
                         .size(page.getSize())
                         .upOrDown(page.getCondition().getUpOrDown())
                         .orderBy(OrderByEnum.getOrderBy(page.getCondition().getOrderBy()))
                         .isSell(page.getCondition().getIsSell()).id(id).build())
       );
    }
}
