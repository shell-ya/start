package com.starnft.star.application.process.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Harlan
 * @date 2022/06/05 20:36
 */
@Data
@ApiModel("支付密码校验参数")
public class PayPwdCheckReq implements Serializable {

    @ApiModelProperty("前置令牌")
    @NotBlank(message = "令牌不能为空")
    private String token;

    @ApiModelProperty("支付密码")
    @NotBlank(message = "支付密码不能为空")
    private String payPassword;
}
