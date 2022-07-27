package com.starnft.star.application.process.scope.impl;

import com.starnft.star.application.process.scope.IScopeCore;

import com.starnft.star.application.process.scope.model.ScoreDTO;
import com.starnft.star.application.process.scope.model.SubScoreDTO;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.scope.model.req.AddScoreRecordReq;
import com.starnft.star.domain.scope.model.req.UpdateUserScopeReq;
import com.starnft.star.domain.scope.model.req.UserScopeReq;
import com.starnft.star.domain.scope.model.res.UserScopeRes;
import com.starnft.star.domain.scope.service.IScopeRecordService;
import com.starnft.star.domain.scope.service.IUserScopeService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Component
public class ScopeCopeImpl implements IScopeCore {
    @Resource
    IUserScopeService iUserScopeService;
    @Resource
    IScopeRecordService iScopeRecordService;

    @Override
    @Transactional
    public void userScopeManageAdd(ScoreDTO addScoreDTO) {
        // 添加积分&积分记录
        UpdateUserScopeReq updateUserScopeReq = getUpdateUserScopeReq(addScoreDTO);
        iUserScopeService.updateUserScopeByUserId(updateUserScopeReq);
        AddScoreRecordReq addScoreRecordReq = getScoreRecordReq(addScoreDTO);
        iScopeRecordService.insertScopeRecord(addScoreRecordReq);
    }

    @Transactional
    public void userScopeManageSub(ScoreDTO subScoreDTO) {
        // 减少积分&积分记录
        UserScopeReq userScopeReq = getUserScopeReq(subScoreDTO);
        UserScopeRes userScopeRes = iUserScopeService.getUserScopeByUserId(userScopeReq);
        Assert.isTrue(userScopeRes.getScope().compareTo(subScoreDTO.getScope()) >= 0, () -> new StarException("积分不足"));
        UpdateUserScopeReq updateUserScopeReq = getUpdateUserScopeReq(subScoreDTO, userScopeReq, userScopeRes);
        iUserScopeService.updateScopeByUserId(updateUserScopeReq);
        AddScoreRecordReq scoreRecordReq = getScoreRecordReq(subScoreDTO);
        iScopeRecordService.insertScopeRecord(scoreRecordReq);
    }

    private UpdateUserScopeReq getUpdateUserScopeReq(ScoreDTO subScoreDTO, UserScopeReq userScopeReq, UserScopeRes userScopeRes) {
        UpdateUserScopeReq updateUserScopeReq = new UpdateUserScopeReq();
        BigDecimal newScope = userScopeRes.getScope().subtract(subScoreDTO.getScope());
        updateUserScopeReq.setScope(newScope);
        updateUserScopeReq.setVersion(userScopeRes.getVersion());
        updateUserScopeReq.setUserId(userScopeReq.getUserId());
        updateUserScopeReq.setScopeType(userScopeReq.getScopeType());
        return updateUserScopeReq;
    }

    private UserScopeReq getUserScopeReq(ScoreDTO subScoreDTO) {
        UserScopeReq userScopeReq = new UserScopeReq();
        userScopeReq.setScopeType(subScoreDTO.getScopeType());
        userScopeReq.setUserId(subScoreDTO.getUserId());
        return userScopeReq;
    }


    private UpdateUserScopeReq getUpdateUserScopeReq(ScoreDTO addScoreDTO) {
        UpdateUserScopeReq updateUserScopeReq = new UpdateUserScopeReq();
        updateUserScopeReq.setUserId(addScoreDTO.getUserId());
        updateUserScopeReq.setScopeType(addScoreDTO.getScopeType());
        updateUserScopeReq.setScope(addScoreDTO.getScope());
        return updateUserScopeReq;
    }

    private AddScoreRecordReq getScoreRecordReq(ScoreDTO addScoreDTO) {
        AddScoreRecordReq addScoreRecordReq = new AddScoreRecordReq();
        addScoreRecordReq.setScope(addScoreDTO.getScope());
        addScoreRecordReq.setUserId(addScoreDTO.getUserId());
        Boolean isSub = Optional.ofNullable(addScoreDTO.getIsSub()).orElse(Boolean.FALSE);
        if (isSub) {
            addScoreRecordReq.setMold(StarConstants.ScopeMold.DOWN);
        } else {
            addScoreRecordReq.setMold(StarConstants.ScopeMold.UP);
        }
        addScoreRecordReq.setRemarks(String.format(addScoreDTO.getTemplate(), addScoreDTO.getScope()));
        addScoreRecordReq.setScopeType(addScoreDTO.getScopeType());
        addScoreRecordReq.setCreatedAt(new Date());
        return addScoreRecordReq;
    }

}
