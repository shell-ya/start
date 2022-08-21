package com.starnft.star.domain.given.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel
public class GivenMangeReq {
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("支付密码")
    private String payPassword;
    @ApiModelProperty("编号ID")
    private Long numberId;
    @ApiModelProperty("主题ID")
    private Long themeId;
    @ApiModelProperty("系列ID")
    private Long seriesId;
}
