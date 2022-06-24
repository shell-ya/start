package com.starnft.star.domain.scope.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserScopeReq {
    private  Long userId;
    private Integer scopeType;
}
