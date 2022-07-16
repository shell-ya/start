package com.starnft.star.domain.scope.model.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "积分类型 0：默认积分 1：交易积分 2：活跃积分 3：流转积分")
    private Integer scopeType;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @ApiModelProperty(value = "用户总积分")
    private BigDecimal scope;
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

}
