package com.starnft.star.interfaces.controller.draw;

import com.starnft.star.application.process.draw.IActivityDrawProcess;
import com.starnft.star.application.process.draw.req.DrawProcessReq;
import com.starnft.star.application.process.limit.ICurrentLimiter;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.component.RedisUtil;
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

    final ICurrentLimiter currentLimiter;

    final RedisUtil redisUtil;

    @ApiOperation("执行抽奖")
    @PostMapping("/dodraw")
    public RopResponse<DrawAwardVO> list(@RequestBody DrawProcessReq drawReq) {

        if (!currentLimiter.tryAcquire()) {
            throw new StarException(StarError.REQUEST_OVERFLOW_ERROR);
        }
        drawReq.setuId(String.valueOf(UserContext.getUserId().getUserId()));
        String redisKey = String.format(RedisKey.VISIT_TIMES_MAPPING.getKey(), drawReq.getuId());
        Long times = redisUtil.incr(redisKey, 1L);
        redisUtil.expire(redisKey, RedisKey.VISIT_TIMES_MAPPING.getTime());
        if (times > 5) {
            redisUtil.hincr(RedisKey.DANGER_LIST_RECORD.getKey(), drawReq.getuId(), 1L);
            throw new StarException(StarError.REQUEST_OVERFLOW_ERROR);
        }
//        throw new StarException(StarError.SYSTEM_ERROR, "开放时间请关注官方公告");

        return RopResponse.success(activityDrawProcess.doDrawProcess(drawReq).getDrawAwardVO());
    }


    @ApiOperation("抽奖记录")
    @PostMapping("/record")
    public RopResponse<ResponsePageResult<DrawAwardExportVO>> queryDrawRecord(@RequestBody DrawAwardExportsReq req) {
        req.setUId(String.valueOf(UserContext.getUserId().getUserId()));
        return RopResponse.success(drawExec.queryDrawRecords(req));
    }

}
