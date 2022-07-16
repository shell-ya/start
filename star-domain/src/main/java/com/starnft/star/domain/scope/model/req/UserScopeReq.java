package com.starnft.star.domain.scope.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class UserScopeReq {
    @ApiModelProperty(value = "用户id")
    private  Long userId;
    @ApiModelProperty(value = "积分类型 0：默认积分 1：交易积分 2：活跃积分 3：流转积分")
    private Integer scopeType;
}
