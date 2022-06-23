package com.starnft.star.interfaces.controller.scope;

import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.scope.model.req.QueryScoreRecordReq;
import com.starnft.star.domain.scope.model.res.ScoreRecordRes;
import com.starnft.star.domain.scope.service.IScopeRecordService;
import com.starnft.star.interfaces.interceptor.UserContext;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "积分接口「WalletController」")
@RequestMapping(value = "/scope")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserScopeController {
    private final IScopeCore iScopeCore;
    private final IScopeRecordService iScopeRecordService;

    @PostMapping("/record")
    public RopResponse<ResponsePageResult<ScoreRecordRes>> record(@RequestBody RequestPage request){
        QueryScoreRecordReq queryScoreRecordReq = new QueryScoreRecordReq();
        queryScoreRecordReq.setUserId(UserContext.getUserId().getUserId());
        queryScoreRecordReq.setPage(request.getPage());
        queryScoreRecordReq.setSize(request.getSize());
        return RopResponse.success(this.iScopeRecordService.getScoreRecord(queryScoreRecordReq));
    }
}
