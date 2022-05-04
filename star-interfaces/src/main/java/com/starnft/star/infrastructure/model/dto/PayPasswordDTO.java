package com.starnft.star.infrastructure.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author WeiChun
 */
@Data
public class PayPasswordDTO {

    @ApiModelProperty("新的支付密码")
    private String newPassword;

    @ApiModelProperty("旧的支付密码")
    private String oldPassword;
}
