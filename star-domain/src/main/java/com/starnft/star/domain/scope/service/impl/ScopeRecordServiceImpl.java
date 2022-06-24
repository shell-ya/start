package com.starnft.star.domain.scope.service.impl;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.scope.model.req.QueryScoreRecordReq;
import com.starnft.star.domain.scope.model.res.ScoreRecordRes;
import com.starnft.star.domain.scope.repository.IScopeRecordRepository;
import com.starnft.star.domain.scope.service.IScopeRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ScopeRecordServiceImpl implements IScopeRecordService {
    @Resource
    IScopeRecordRepository iScopeRecordRepository;

    @Override
    public ResponsePageResult<ScoreRecordRes> getScoreRecord(QueryScoreRecordReq queryScoreRecordReq) {
        return  iScopeRecordRepository.queryScopeRecordPageByUserId(queryScoreRecordReq);
    }
}
