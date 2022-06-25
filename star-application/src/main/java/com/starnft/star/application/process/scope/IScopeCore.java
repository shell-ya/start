package com.starnft.star.application.process.scope;


import com.starnft.star.application.process.scope.model.ScopeMessage;

public interface IScopeCore {
    void calculateUserScope(ScopeMessage userScopeMessageVO);
}
