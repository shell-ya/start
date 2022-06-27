package com.starnft.star.domain.scope.model.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    private BigDecimal scope;
    private Integer version;

}
