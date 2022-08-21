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
    @ApiModelProperty("编码ID")
    private Long numberId;
    @ApiModelProperty("编码ID")
    private Long themeId;
    @ApiModelProperty("编码ID")
    private Long seriesId;
}
