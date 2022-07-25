package com.starnft.star.interfaces.controller.bulletin;


import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.bulletin.IBulletinService;
import com.starnft.star.domain.bulletin.model.dto.BulletinPageDto;
import com.starnft.star.domain.bulletin.model.vo.BulletinVo;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Date 2022/7/24 4:42 PM
 * @Author ： shellya
 */
@Api(tags="公告相关接口")
@RestController
@RequestMapping("/bulletin")
public class BulletinController {

    @Autowired
    private IBulletinService bulletinService;

    @ApiOperation("公告列表")
    @GetMapping("/list")
    @TokenIgnore
    public RopResponse<ResponsePageResult<BulletinVo>> bulletinList(@RequestBody(required = false) BulletinPageDto dto){
        if (Objects.isNull(dto)) {
            dto = new BulletinPageDto(1,100);
        }
        return RopResponse.success(bulletinService.queryBulletinList(dto));
    }

    @ApiOperation("公告详情")
    @GetMapping("/detail/{id}")
    @TokenIgnore
    public RopResponse<BulletinVo> getBulletinDetail(@PathVariable Long id){
        return RopResponse.success(bulletinService.getBulletinDetail(id));
    }
}
