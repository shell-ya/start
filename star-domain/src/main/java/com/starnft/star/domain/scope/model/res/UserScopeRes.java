package com.starnft.star.domain.scope.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserScopeRes {
    private Integer scopeType;
    private Long userId;
    private BigDecimal scope;

}
