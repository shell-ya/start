package com.starnft.star.application.process.event.strategy;

import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.AddScoreDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("scopeStrategy")
public  class ScopeStrategy {
     @Resource
     private IScopeCore iScopeCore;
     public void addScope(AddScoreDTO addScoreDTO){
         iScopeCore.userScopeManageAdd(addScoreDTO);
     }
}
