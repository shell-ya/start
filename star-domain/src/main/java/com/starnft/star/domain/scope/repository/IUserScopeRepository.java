package com.starnft.star.domain.scope.repository;

import com.starnft.star.domain.scope.model.req.AddUserScopeReq;
import com.starnft.star.domain.scope.model.req.UpdateUserScopeReq;
import com.starnft.star.domain.scope.model.req.UserScopeReq;
import com.starnft.star.domain.scope.model.res.UserScopeRes;

public interface IUserScopeRepository {
    UserScopeRes queryUserScopeByUserId(UserScopeReq req);
    Boolean updateUserScopeByUserId(UpdateUserScopeReq req);
    Boolean insertUserScopeByUserId(AddUserScopeReq req);
//    Boolean updateUserScopeByUserId(UpdateUserScopeReq req);
}
