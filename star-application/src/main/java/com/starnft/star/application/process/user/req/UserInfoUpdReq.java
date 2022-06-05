package com.starnft.star.application.process.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Harlan
 * @date 2022/06/05 14:23
 */
@Data
@ApiModel("用户信息更新参数")
public class UserInfoUpdReq implements Serializable {

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("简介")
    private String briefIntroduction;
    
}
