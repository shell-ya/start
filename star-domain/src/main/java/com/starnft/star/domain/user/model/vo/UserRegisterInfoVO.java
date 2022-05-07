package com.starnft.star.domain.user.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author WeiChunLAI
 */
@Data
public class UserRegisterInfoVO {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("token")
    private String token;
}
