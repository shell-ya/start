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
        UserScopeReq userScopeReq = getUserScopeReq(updateUserScopeReq);
        UserScopeRes resultUserScopeRes = userScopeRepository.queryUserScopeByUserId(userScopeReq);
       if (Objects.nonNull(resultUserScopeRes)){
           UpdateUserScopeReq insertScope = new UpdateUserScopeReq();
           extractedUpdateUserScope(updateUserScopeReq, userScopeReq, resultUserScopeRes, insertScope);
           userScopeRepository.updateUserScopeByUserId(insertScope);
           resultUpdateExtracted(updateUserScopeReq, resultUserScopeRes);

       }else{
           AddUserScopeReq addUserScopeReq = new AddUserScopeReq();
           extractedInsertUserScope(updateUserScopeReq, addUserScopeReq);
           userScopeRepository.insertUserScopeByUserId(addUserScopeReq);
           resultUserScopeRes=new UserScopeRes();
           resultInsertExtracted(updateUserScopeReq, resultUserScopeRes);
       }
        return resultUserScopeRes;
    }

    private void resultInsertExtracted(UpdateUserScopeReq updateUserScopeReq, UserScopeRes resultUserScopeRes) {

        resultUserScopeRes.setScope(updateUserScopeReq.getScope());
        resultUserScopeRes.setScopeType(updateUserScopeReq.getScopeType());
        resultUserScopeRes.setUserId(updateUserScopeReq.getUserId());
        resultUserScopeRes.setVersion(0);
    }

    private void resultUpdateExtracted(UpdateUserScopeReq updateUserScopeReq, UserScopeRes resultUserScopeRes) {
        resultUserScopeRes.setScope(resultUserScopeRes.getScope().add(updateUserScopeReq.getScope()));
        resultUserScopeRes.setScopeType(resultUserScopeRes.getScopeType());
        resultUserScopeRes.setUserId(resultUserScopeRes.getUserId());
        resultUserScopeRes.setVersion(resultUserScopeRes.getVersion()+1);
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

    private UserScopeReq getUserScopeReq(UpdateUserScopeReq updateUserScopeReq) {
        UserScopeReq userScopeReq = new UserScopeReq();
        userScopeReq.setUserId(updateUserScopeReq.getUserId());
        userScopeReq.setScopeType(updateUserScopeReq.getScopeType());
        return userScopeReq;
    }

    private void extractedInsertUserScope(UpdateUserScopeReq updateUserScopeReq, AddUserScopeReq addUserScopeReq) {
        addUserScopeReq.setScope(updateUserScopeReq.getScope());
        addUserScopeReq.setUserId(updateUserScopeReq.getUserId());
        addUserScopeReq.setScopeType(updateUserScopeReq.getScopeType());
    }

    private void extractedUpdateUserScope(UpdateUserScopeReq updateUserScopeReq, UserScopeReq userScopeReq, UserScopeRes resultUserScopeRes, UpdateUserScopeReq insertScope) {
        BigDecimal totalScope = resultUserScopeRes.getScope().add(updateUserScopeReq.getScope());
        insertScope.setScope(totalScope);
        insertScope.setScopeType(updateUserScopeReq.getScopeType());
        insertScope.setUserId(userScopeReq.getUserId());
        insertScope.setVersion(resultUserScopeRes.getVersion());
    }
}
