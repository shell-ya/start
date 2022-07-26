package com.starnft.star.application.process.scope;



import com.starnft.star.application.process.scope.model.ScoreDTO;

public interface IScopeCore {
    void userScopeManageAdd(ScoreDTO addScoreDTO);
    public void userScopeManageSub(ScoreDTO subScoreDTO);
}
