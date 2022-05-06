package com.starnft.star.domain.model.vo;

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

    @ApiModelProperty("是否实名认证")
    private Boolean isCertified;
}
