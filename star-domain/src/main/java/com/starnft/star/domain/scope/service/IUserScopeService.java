package com.starnft.star.domain.scope.service;

import com.starnft.star.domain.scope.model.req.UpdateUserScopeReq;
import com.starnft.star.domain.scope.model.req.UserScopeReq;
import com.starnft.star.domain.scope.model.res.UserScopeRes;

public interface IUserScopeService {

   public UserScopeRes updateUserScopeByUserId(UpdateUserScopeReq userScopeReq);
   public UserScopeRes getUserScopeByUserId(UserScopeReq userScopeReq);
}
