package com.starnft.star.domain.rank.core.rank.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Date 2022/7/7 7:34 PM
 * @Author ： shellya
 */
@Data
@ApiModel
public class InvitationHistoryItem {

    @ApiModelProperty(value = "用户名")
    private String nickName;
    @ApiModelProperty(value = "手机号")
    private String mobile ;
    @ApiModelProperty(value = "账户")
    private String account;
    @ApiModelProperty(value = "是否有效")
    private Integer valid;
//    @ApiModelProperty(value = "邀请时间")
//    private Date InvitationTime;
}
