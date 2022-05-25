package com.starnft.star.interfaces.controller.mall;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.numbers.model.req.NumberQueryRequest;
import com.starnft.star.domain.numbers.model.vo.NumberDetailVO;
import com.starnft.star.domain.numbers.model.vo.NumberVO;
import com.starnft.star.domain.numbers.serivce.NumberService;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Harlan
 * @date 2022/05/23 12:15
 */
@RestController
@Api(tags = "商品相关接口「NumbersController」")
@RequestMapping("/numbers")
@RequiredArgsConstructor
public class NumbersController {

    private final NumberService numberService;

    @PostMapping("/list")
    @ApiOperation("获取商品列表")
    @TokenIgnore
    public RopResponse<ResponsePageResult<NumberVO>> getNumberList(@RequestBody RequestConditionPage<NumberQueryRequest> request) {
        return RopResponse.success(this.numberService.listNumber(request));
    }

    @GetMapping("/{id}")
    @ApiOperation("获取商品详情")
    @TokenIgnore
    public RopResponse<NumberDetailVO> getNumber(@PathVariable @ApiParam("商品id") Long id) {
        return RopResponse.success(this.numberService.getNumberById(id));
    }

}
