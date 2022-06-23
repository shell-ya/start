package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.scope.model.req.AddScoreRecordReq;
import com.starnft.star.domain.scope.model.req.QueryScoreRecordReq;
import com.starnft.star.domain.scope.model.res.ScoreRecordRes;
import com.starnft.star.domain.scope.repository.IScopeRecordRepository;
import com.starnft.star.infrastructure.entity.scope.StarNftScopeRecord;
import com.starnft.star.infrastructure.mapper.scope.StarNftScopeRecordMapper;
import com.starnft.star.infrastructure.tools.PageHelperInterface;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ScopeRecordRepository implements IScopeRecordRepository, PageHelperInterface {
    @Resource
    StarNftScopeRecordMapper starNftScopeRecordMapper;


    @Override
    public ResponsePageResult<ScoreRecordRes> queryScopeRecordPageByUserId(QueryScoreRecordReq queryScoreRecordReq) {

        PageInfo<StarNftScopeRecord> repository = PageHelper.startPage(queryScoreRecordReq.getPage(), queryScoreRecordReq.getSize()).doSelectPageInfo(() -> {
            starNftScopeRecordMapper.selectList(new QueryWrapper<StarNftScopeRecord>()
                    .eq(StarNftScopeRecord.COL_USER_ID, queryScoreRecordReq.getUserId())
                    .orderByDesc(StarNftScopeRecord.COL_ID)
            );
        });
        List<ScoreRecordRes> collect = repository.getList()
                .stream()
                .map(item -> ScoreRecordRes.builder()
                        .id(item.getId())
                        .scope(item.getScope())
                        .createdAt(item.getCreatedAt())
                        .mold(item.getMold())
                        .remarks(item.getRemarks())
                        .build())
                .collect(Collectors.toList());

        return listReplace(repository,collect);
    }

    @Override
    public Boolean queryScopeRecordPagesByUserId(AddScoreRecordReq scoreRecordReq) {
        return null;
    }
}
