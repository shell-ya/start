package com.starnft.star.infrastructure.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author WeiChunLAI
 */
@Data
public class UserRegisterInfoVO {

    @ApiModelProperty("用户id")
    private Long userId;
}
