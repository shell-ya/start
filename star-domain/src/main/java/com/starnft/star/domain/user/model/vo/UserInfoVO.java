package com.starnft.star.domain.user.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author LaIWeiChun
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {

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
    private Boolean realPersonFlag;

    @ApiModelProperty("区块链地址")
    private String blockchainAddress;

    @ApiModelProperty("简介")
    private String briefIntroduction;

    @ApiModelProperty("支付密码")
    private String plyPassword;
}
