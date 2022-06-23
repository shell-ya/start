package com.starnft.star.domain.scope.service;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.scope.model.req.QueryScoreRecordReq;
import com.starnft.star.domain.scope.model.res.ScoreRecordRes;

public interface IScopeRecordService {
    ResponsePageResult<ScoreRecordRes> getScoreRecord(QueryScoreRecordReq queryScoreRecordReq);
}
