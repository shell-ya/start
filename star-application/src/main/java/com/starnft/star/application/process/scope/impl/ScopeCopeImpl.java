package com.starnft.star.application.process.scope.impl;

import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.AddScoreDTO;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.scope.model.req.AddScoreRecordReq;
import com.starnft.star.domain.scope.model.req.UpdateUserScopeReq;
import com.starnft.star.domain.scope.service.IScopeRecordService;
import com.starnft.star.domain.scope.service.IUserScopeService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class ScopeCopeImpl implements IScopeCore {
    @Resource
    IUserScopeService iUserScopeService;
    @Resource
    IScopeRecordService iScopeRecordService;

    @Override
    @Transactional
    public void userScopeManageAdd(AddScoreDTO addScoreDTO) {
          // 添加积分&积分记录
          UpdateUserScopeReq updateUserScopeReq = getUpdateUserScopeReq(addScoreDTO);
          iUserScopeService.updateUserScopeByUserId(updateUserScopeReq);
          AddScoreRecordReq addScoreRecordReq = getAddScoreRecordReq(addScoreDTO);
          iScopeRecordService.insertScopeRecord(addScoreRecordReq);
    }












    private UpdateUserScopeReq getUpdateUserScopeReq(AddScoreDTO addScoreDTO) {
        UpdateUserScopeReq updateUserScopeReq = new UpdateUserScopeReq();
        updateUserScopeReq.setUserId(addScoreDTO.getUserId());
        updateUserScopeReq.setScopeType(addScoreDTO.getScopeType());
        updateUserScopeReq.setScope(addScoreDTO.getScope());
        return updateUserScopeReq;
    }

    private AddScoreRecordReq getAddScoreRecordReq(AddScoreDTO addScoreDTO) {
        AddScoreRecordReq addScoreRecordReq = new AddScoreRecordReq();
        addScoreRecordReq.setScope(addScoreDTO.getScope());
        addScoreRecordReq.setUserId(addScoreDTO.getUserId());
        addScoreRecordReq.setMold(StarConstants.ScopeMold.UP);
        addScoreRecordReq.setRemarks(String.format(addScoreDTO.getTemplate(), addScoreDTO.getScope()));
        addScoreRecordReq.setScopeType(addScoreDTO.getScopeType());
        addScoreRecordReq.setCreatedAt(new Date());
        return addScoreRecordReq;
    }

}
