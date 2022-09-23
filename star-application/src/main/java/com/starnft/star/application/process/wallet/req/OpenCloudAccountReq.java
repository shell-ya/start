package com.starnft.star.application.process.wallet.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class OpenCloudAccountReq {
    @ApiModelProperty("开通成功后的跳转地址")
    private String returnUri;
}
