package com.starnft.star.interfaces.controller.scope;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.scope.model.req.QueryScoreRecordReq;
import com.starnft.star.domain.scope.model.req.UserScopeReq;
import com.starnft.star.domain.scope.model.res.ScoreRecordRes;
import com.starnft.star.domain.scope.model.res.UserScopeRes;
import com.starnft.star.domain.scope.service.IScopeRecordService;
import com.starnft.star.domain.scope.service.IUserScopeService;
import com.starnft.star.interfaces.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "积分接口「UserScopeController」")
@RequestMapping(value = "/scope")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserScopeController {
    private final IScopeRecordService iScopeRecordService;
    private final IUserScopeService iUserScopeService;
    @ApiOperation("积分记录")
    @PostMapping("/record")
    public RopResponse<ResponsePageResult<ScoreRecordRes>> record(@RequestBody RequestConditionPage<Integer> request){
        QueryScoreRecordReq queryScoreRecordReq = getQueryScoreRecordReq(request);
        return RopResponse.success(this.iScopeRecordService.getScoreRecord(queryScoreRecordReq));
    }
    @ApiOperation("个人积分获取")
    @PostMapping("/info")
    public RopResponse<UserScopeRes> info(@RequestBody UserScopeReq request){
        request.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(this.iUserScopeService.getUserScopeByUserId(request));
    }


    private QueryScoreRecordReq getQueryScoreRecordReq(RequestConditionPage<Integer> request) {
        QueryScoreRecordReq queryScoreRecordReq = new QueryScoreRecordReq();
        queryScoreRecordReq.setUserId(UserContext.getUserId().getUserId());
        queryScoreRecordReq.setPage(request.getPage());
        queryScoreRecordReq.setScopeType(request.getCondition());
        queryScoreRecordReq.setSize(request.getSize());
        return queryScoreRecordReq;
    }

}
