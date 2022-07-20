package com.starnft.star.business.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserScopeAddDTO {
    private Long account;
    private BigDecimal userScope;
    private Long scopeType;
    private String remarks;

}
