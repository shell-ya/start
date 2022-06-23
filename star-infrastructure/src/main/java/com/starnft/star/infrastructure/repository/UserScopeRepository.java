package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starnft.star.domain.scope.model.req.UpdateUserScopeReq;
import com.starnft.star.domain.scope.model.req.UserScopeReq;
import com.starnft.star.domain.scope.model.res.UserScopeRes;
import com.starnft.star.domain.scope.repository.IUserScopeRepository;
import com.starnft.star.infrastructure.entity.scope.StarNftUserScope;
import com.starnft.star.infrastructure.mapper.scope.StarNftUserScopeMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

@Repository
public class UserScopeRepository implements IUserScopeRepository {
    @Resource
   private StarNftUserScopeMapper starNftUserScopeMapper;
    public UserScopeRes queryUserScopeByUserId(UserScopeReq req){
        StarNftUserScope starNftUserScope = starNftUserScopeMapper.selectOne(new QueryWrapper<StarNftUserScope>().eq(StarNftUserScope.COL_USER_ID, req.getUserId()));
        UserScopeRes userScopeRes = new UserScopeRes();
        userScopeRes.setScope(starNftUserScope.getUserScope());
        userScopeRes.setUserId(starNftUserScope.getUserId());
        return  userScopeRes;
    }

    @Override
    public Boolean updateUserScopeByUserId(UpdateUserScopeReq req) {
          StarNftUserScope starNftUserScope = new StarNftUserScope();
          starNftUserScope.setUserScope(req.getScope());
          starNftUserScope.setModifiedAt(new Date());
          starNftUserScopeMapper.update(starNftUserScope, new QueryWrapper<StarNftUserScope>().eq(StarNftUserScope.COL_USER_ID, req.getUserId()));
          return Boolean.TRUE;
    }
}
