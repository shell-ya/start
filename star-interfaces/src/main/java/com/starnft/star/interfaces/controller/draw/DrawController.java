package com.starnft.star.interfaces.controller.draw;

import com.starnft.star.common.RopResponse;
import com.starnft.star.domain.draw.model.req.DrawReq;
import com.starnft.star.domain.draw.model.vo.DrawAwardVO;
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

    @ApiOperation("执行抽奖")
    @PostMapping("/dodraw")
    public RopResponse<DrawAwardVO> list(@RequestBody DrawReq drawReq) {
        return RopResponse.success(mockResult(UserContext.getUserId().getUserId()));
    }

    private DrawAwardVO mockResult(Long userId) {
        DrawAwardVO drawAwardVO = new DrawAwardVO();
        drawAwardVO.setuId(String.valueOf(userId));
        drawAwardVO.setAwardId("100001");
        drawAwardVO.setAwardContent("1000积分");
        drawAwardVO.setAwardName("1000积分");
        drawAwardVO.setAwardType(1);
        drawAwardVO.setStrategyMode(1);
        drawAwardVO.setGrantType(1);
        drawAwardVO.setAwardPic("https://banner-1302318928.cos.ap-shanghai.myqcloud.com/theme/1660463323376_661af3ec.png");

        return drawAwardVO;

    }
}
