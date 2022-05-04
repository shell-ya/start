package com.starnft.star.infrastructure.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author LaIWeiChun
 */
@Data
public class UserInfoVO {

    @ApiModelProperty("用户id")
    private Long account;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像地址")
    private String avatar;

    @ApiModelProperty("token")
    private String token;
}
