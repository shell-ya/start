package com.starnft.star.domain.scope.repository;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.scope.model.req.AddScoreRecordReq;
import com.starnft.star.domain.scope.model.req.QueryScoreRecordReq;
import com.starnft.star.domain.scope.model.res.ScoreRecordRes;

public interface IScopeRecordRepository {
   ResponsePageResult<ScoreRecordRes>  queryScopeRecordPageByUserId(QueryScoreRecordReq  queryScoreRecordReq);
   Boolean queryScopeRecordPagesByUserId(AddScoreRecordReq scoreRecordReq);

}
