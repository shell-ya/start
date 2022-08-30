package com.starnft.star.interfaces.controller.draw;

import com.starnft.star.application.process.draw.IActivityDrawProcess;
import com.starnft.star.application.process.draw.IDrawDelProcess;
import com.starnft.star.application.process.draw.req.DrawProcessReq;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.draw.model.req.DrawAwardExportsReq;
import com.starnft.star.domain.draw.model.vo.DrawAwardExportVO;
import com.starnft.star.domain.draw.model.vo.DrawAwardVO;
import com.starnft.star.domain.draw.service.draw.IDrawExec;
import com.starnft.star.interfaces.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "抽奖「DrawController」")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/draw")
public class DrawController {

    final IActivityDrawProcess activityDrawProcess;

    final IDrawExec drawExec;

    final IDrawDelProcess drawDelProcess;

    @ApiOperation("执行抽奖")
    @PostMapping("/dodraw")
    public RopResponse<DrawAwardVO> list(@RequestBody DrawProcessReq drawReq) {
        drawReq.setuId(String.valueOf(UserContext.getUserId().getUserId()));
        return RopResponse.success(activityDrawProcess.doDrawProcess(drawReq).getDrawAwardVO());
    }


    @ApiOperation("抽奖记录")
    @PostMapping("/record")
    public RopResponse<ResponsePageResult<DrawAwardExportVO>> queryDrawRecord(@RequestBody DrawAwardExportsReq req) {
        req.setUId(String.valueOf(UserContext.getUserId().getUserId()));
        return RopResponse.success(drawExec.queryDrawRecords(req));
    }

    @ApiOperation("清理异常抽奖数据")
    @PostMapping("/delDrawExport")
    public RopResponse delDrawExport(){
        drawDelProcess.delErrorDraw();
        return RopResponse.success(true);
    }

    @ApiOperation("重新分配编号")
    @PostMapping("/reNumber")
    public RopResponse reNumber(){
        drawDelProcess.reNumber();
        return RopResponse.success(true);
    }

}
