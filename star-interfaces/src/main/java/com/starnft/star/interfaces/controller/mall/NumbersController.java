package com.starnft.star.interfaces.controller.mall;

import com.starnft.star.application.process.number.INumberCore;
import com.starnft.star.application.process.number.res.ConsignDetailRes;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.number.model.req.NumberConsignmentCancelRequest;
import com.starnft.star.domain.number.model.req.NumberConsignmentRequest;
import com.starnft.star.domain.number.model.req.NumberQueryRequest;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import com.starnft.star.interfaces.interceptor.UserResolverInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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

    private final INumberCore numberCore;

    @PostMapping("/list")
    @ApiOperation("获取商品列表")
    @TokenIgnore
    public RopResponse<ResponsePageResult<NumberVO>> getNumberList(@RequestBody RequestConditionPage<NumberQueryRequest> request) {
        return RopResponse.success(this.numberCore.obtainThemeNumberList(request));
    }

    @GetMapping("/{id}")
    @ApiOperation("获取商品详情")
    public RopResponse<NumberDetailVO> getNumber(@PathVariable @ApiParam("商品id") Long id) {
        return RopResponse.success(this.numberCore.obtainThemeNumberDetail(id));
    }

    @GetMapping("/consignDetail/{id}")
    @ApiOperation("加载商品寄售界面数据")
    public RopResponse<ConsignDetailRes> obtainConsignDetail(@PathVariable @ApiParam("商品id") Long id) {
        return RopResponse.success(this.numberCore.obtainConsignDetail(id));
    }

    @PostMapping("/consign")
    @ApiOperation("商品寄售")
    public RopResponse<Boolean> consignment(UserResolverInfo userResolverInfo,
                                            @Validated @RequestBody NumberConsignmentRequest request) {
        return RopResponse.success(this.numberCore.consignment(userResolverInfo.getUserId(), request));
    }

    @PostMapping("/consignCancel")
    @ApiOperation("商品取消寄售")
    public RopResponse<Boolean> cancelConsignment(UserResolverInfo userResolverInfo,
                                                  @Validated @RequestBody NumberConsignmentCancelRequest request) {
        return RopResponse.success(this.numberCore.consignmentCancel(userResolverInfo.getUserId(), request));
    }


}
