package com.starnft.star.application.process.draw.strategy;

import com.starnft.star.application.process.draw.req.ConsumablesVerifyReq;
import com.starnft.star.application.process.draw.vo.DrawConsumeVO;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.ScoreDTO;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.scope.model.req.UserScopeReq;
import com.starnft.star.domain.scope.model.res.UserScopeRes;
import com.starnft.star.domain.scope.service.IUserScopeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Component("scopeConsumeExec")
public class ScopeConsumeExec implements DrawConsumeExecutor {

    @Resource
    IScopeCore scopeCore;

    @Resource
    IUserScopeService iUserScopeService;

    @Override
    public Boolean doConsume(DrawConsumeVO consumeVO) {
        try {
            scopeCore.userScopeManageSub(buildScopeReq(consumeVO));
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doVerify(ConsumablesVerifyReq req) {
        UserScopeReq userScopeReq = new UserScopeReq(req.getUid(), 0);
        UserScopeRes userScopeRes = iUserScopeService.getUserScopeByUserId(userScopeReq);
        Assert.isTrue(userScopeRes.getScope().compareTo(new BigDecimal(1000)) >= 0, () -> new StarException("积分不足"));
    }

    private ScoreDTO buildScopeReq(DrawConsumeVO drawConsumeVO) {
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setUserId(Long.parseLong(drawConsumeVO.getDrawAwardVO().getuId()));
        scoreDTO.setScope(new BigDecimal(1000));
        scoreDTO.setScopeType(0);
        scoreDTO.setIsSub(true);
        scoreDTO.setTemplate("参与宇宙回响活动抽奖消耗%s元石");
        return scoreDTO;
    }
}
