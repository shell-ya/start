package com.starnft.star.interfaces.controller.bulletin;

import com.starnft.star.application.process.bulletin.IBulletinCore;
import com.starnft.star.application.process.bulletin.res.BulletinDetailVo;
import com.starnft.star.application.process.bulletin.res.BulletinVo;
import com.starnft.star.application.process.rank.req.RankReq;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Date 2022/7/24 4:42 PM
 * @Author ： shellya
 */
@Api(tags="公告相关接口")
@RestController
@RequestMapping("/bulletin")
public class BulletinController {

    private IBulletinCore bulletinCore;

    @ApiOperation("公告列表")
    @GetMapping("/list")
    @TokenIgnore
    public RopResponse<ResponsePageResult<BulletinVo>> bulletinList(){
            return null;
    }

    @ApiOperation("公告详情")
    @GetMapping("/detail/{id}")
    @TokenIgnore
    public RopResponse<BulletinDetailVo> getBulletinDetail(@PathVariable Long id){
        return null;
    }
}
