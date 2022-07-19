package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.scope.model.req.AddUserScopeReq;
import com.starnft.star.domain.scope.model.req.UpdateUserScopeReq;
import com.starnft.star.domain.scope.model.req.UserScopeReq;
import com.starnft.star.domain.scope.model.res.UserScopeRes;
import com.starnft.star.domain.scope.repository.IUserScopeRepository;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.infrastructure.entity.scope.StarNftUserScope;
import com.starnft.star.infrastructure.mapper.scope.StarNftUserScopeMapper;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserScopeRepository   implements IUserScopeRepository {
    @Resource
   private StarNftUserScopeMapper starNftUserScopeMapper;
    @Resource
    private Map<StarConstants.Ids, IIdGenerator> idsIIdGeneratorMap;
    public UserScopeRes queryUserScopeByUserId(UserScopeReq req){
        StarNftUserScope starNftUserScope = starNftUserScopeMapper
                .selectOne(new QueryWrapper<StarNftUserScope>()
                .eq(StarNftUserScope.COL_USER_ID, req.getUserId())
                .eq(StarNftUserScope.COL_SCOPE_TYPE,req.getScopeType())
        );
        if (Objects.isNull(starNftUserScope)) return  null;
        UserScopeRes userScopeRes = new UserScopeRes();
        userScopeRes.setScope(starNftUserScope.getUserScope());
        userScopeRes.setUserId(starNftUserScope.getUserId());
        userScopeRes.setVersion(starNftUserScope.getVersion());
        return  userScopeRes;
    }

    @Override
    public Boolean updateUserScopeByUserId(UpdateUserScopeReq req) {
          StarNftUserScope starNftUserScope = new StarNftUserScope();
          starNftUserScope.setUserScope(req.getScope());
          starNftUserScope.setModifiedAt(new Date());
          starNftUserScope.setVersion(req.getVersion()+1);
      return   starNftUserScopeMapper.update(starNftUserScope,
                new QueryWrapper<StarNftUserScope>()
                        .eq(StarNftUserScope.COL_USER_ID, req.getUserId())
                        .eq(StarNftUserScope.COL_SCOPE_TYPE, req.getScopeType())
                        .eq(StarNftUserScope.COL_VERSION,req.getVersion())
        )>0?Boolean.TRUE:Boolean.FALSE;
    }

    @Override
    public Boolean insertUserScopeByUserId(AddUserScopeReq req) {
        StarNftUserScope starNftUserScope = new StarNftUserScope();

        starNftUserScope.setUserScope(req.getScope());
        starNftUserScope.setModifiedAt(new Date());
        starNftUserScope.setCreatedAt(new Date());
        starNftUserScope.setUserId(req.getUserId());
        starNftUserScope.setScopeType(req.getScopeType());
        starNftUserScope.setId(idsIIdGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId());
        starNftUserScope.setVersion(StarConstants.INIT_VERSION);
        return  starNftUserScopeMapper.insert(starNftUserScope)>0?Boolean.TRUE:Boolean.FALSE;
    }

    @Override
    public UserScopeRes queryUserAllScopeByUserId(UserScopeReq req) {
        List<StarNftUserScope> starNftUserScopes = starNftUserScopeMapper.selectList(
                new QueryWrapper<StarNftUserScope>().eq(StarNftUserScope.COL_USER_ID, req.getUserId()));
        if ( 0 == starNftUserScopes.size()) return null;
        BigDecimal userScope = starNftUserScopes.stream().map(StarNftUserScope::getUserScope).reduce(BigDecimal.ZERO, BigDecimal::add);
        UserScopeRes userScopeRes = new UserScopeRes();
        userScopeRes.setScope(userScope);
        userScopeRes.setUserId(req.getUserId());
//        userScopeRes.setVersion(starNftUserScope.getVersion());
        return userScopeRes;
    }
}
