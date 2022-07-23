package com.starnft.star.interfaces.controller.props;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.prop.model.req.PropShopListReq;
import com.starnft.star.domain.prop.model.req.PropsListReq;
import com.starnft.star.domain.prop.model.res.PropsListRes;
import com.starnft.star.domain.prop.model.vo.PropsVO;
import com.starnft.star.domain.prop.service.IPropsService;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import com.starnft.star.interfaces.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "道具「PropsController」")
@RequestMapping(value = "/props")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropsController {


    private final IPropsService propsService;

    @ApiOperation("用户道具列表")
    @PostMapping("/users/list")
    public RopResponse<List<PropsListRes>> list() {
        PropsListReq req = new PropsListReq(UserContext.getUserId().getUserId());
        return RopResponse.success(this.propsService.obtainProps(req));
    }


    @ApiOperation("用户道具详情")
    @PostMapping("/details/{propsId}")
    @TokenIgnore
    public RopResponse<PropsVO> details(@PathVariable("propsId") String propsId) {
        return RopResponse.success(this.propsService.queryPropsDetails(Long.parseLong(propsId)));
    }

    @ApiOperation("道具商城列表")
    @PostMapping("/shop/list")
    @TokenIgnore
    public RopResponse<ResponsePageResult<PropsVO>> shopList(@RequestBody PropShopListReq propShopListReq) {
        return RopResponse.success(this.propsService.propsShopList(propShopListReq));
    }


}
