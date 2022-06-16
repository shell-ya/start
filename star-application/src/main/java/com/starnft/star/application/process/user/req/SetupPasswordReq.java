package com.starnft.star.application.process.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Harlan
 * @date 2022/06/15 23:40
 */
@Data
@ApiModel("初始化密码请求参数")
public class SetupPasswordReq {
    @ApiModelProperty("密码")
    private String password;
}
