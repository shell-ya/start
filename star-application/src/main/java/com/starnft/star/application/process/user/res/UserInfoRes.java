package com.starnft.star.application.process.user.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author WeiChunLAI
 */
@Data
@ApiModel
public class UserInfoRes {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像地址")
    private String avatar;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("是否实名认证")
    private Boolean isCertified;
}
