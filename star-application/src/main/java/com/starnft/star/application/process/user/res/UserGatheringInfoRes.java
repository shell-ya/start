package com.starnft.star.application.process.user.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel("用户信息")
public class UserGatheringInfoRes implements Serializable {

    @ApiModelProperty("用户id")
    private Long uid;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("区块链地址")
    private String blockchainAddress;

    @ApiModelProperty("钱包地址")
    private String walletId;

    @ApiModelProperty("余额")
    private BigDecimal balance;

    @ApiModelProperty("收款账户")
    private String collectionAccount;

    @ApiModelProperty("简介")
    private String briefIntroduction;

    @ApiModelProperty("是否实名认证")
    private Boolean realPersonFlag;

    @ApiModelProperty("是否设置支付密码")
    private Boolean payPasswordFlag;

}
