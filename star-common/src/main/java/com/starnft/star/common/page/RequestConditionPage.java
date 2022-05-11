package com.starnft.star.common.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "带条件的分页")
public class RequestConditionPage<T> {
    @ApiModelProperty("页数")
    private int page ;
    @ApiModelProperty("个数")
    private  int size;
    @ApiModelProperty("条件")
    private T condition;
}
