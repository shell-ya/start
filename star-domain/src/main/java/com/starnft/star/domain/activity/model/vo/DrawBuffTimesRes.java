package com.starnft.star.domain.activity.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("buff剩余次数响应")
public class DrawBuffTimesRes {

    @ApiModelProperty("buff Id")
    private String awardId;
    @ApiModelProperty("剩余次数")
    private Integer times;
    @ApiModelProperty("乘积")
    private Integer product;
}
