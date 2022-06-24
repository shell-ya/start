package com.starnft.star.domain.scope.service.impl;

import com.starnft.star.domain.scope.model.req.AddUserScopeReq;
import com.starnft.star.domain.scope.model.req.UpdateUserScopeReq;
import com.starnft.star.domain.scope.model.req.UserScopeReq;
import com.starnft.star.domain.scope.model.res.UserScopeRes;
import com.starnft.star.domain.scope.repository.IUserScopeRepository;
import com.starnft.star.domain.scope.service.IUserScopeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserScopeServiceImpl  implements IUserScopeService {
    @Resource
    IUserScopeRepository userScopeRepository;
    @Transactional
    @Override
    public UserScopeRes updateUserScopeByUserId(UpdateUserScopeReq updateUserScopeReq) {
        UserScopeReq userScopeReq = new UserScopeReq();
        userScopeReq.setUserId(updateUserScopeReq.getUserId());
        UserScopeRes resultUserScopeRes = userScopeRepository.queryUserScopeByUserId(userScopeReq);
       if (Objects.nonNull(resultUserScopeRes)){
           UpdateUserScopeReq insertScope = new UpdateUserScopeReq();
           BigDecimal totalScope = resultUserScopeRes.getScope().add(updateUserScopeReq.getScope());
           insertScope.setScope(totalScope);
           insertScope.setScope(totalScope);
           insertScope.setUserId(userScopeReq.getUserId());
           insertScope.setVersion(resultUserScopeRes.getVersion());
           userScopeRepository.updateUserScopeByUserId(insertScope);
       }else{
           AddUserScopeReq addUserScopeReq = new AddUserScopeReq();
           addUserScopeReq.setScope(updateUserScopeReq.getScope());
           addUserScopeReq.setUserId(userScopeReq.getUserId());
           resultUserScopeRes.setScope(updateUserScopeReq.getScope());
           userScopeRepository.insertUserScopeByUserId(addUserScopeReq);
       }
        return resultUserScopeRes;
    }

    @Override
    public UserScopeRes getUserScopeByUserId(UserScopeReq userScopeReq) {
        UserScopeRes resultUserScopeRes = userScopeRepository.queryUserScopeByUserId(userScopeReq);
        return Optional.ofNullable(resultUserScopeRes)
                .orElse(UserScopeRes.builder()
                        .scope(new BigDecimal(0))
                        .userId(userScopeReq.getUserId())
                        .build());
    }
}
