package com.starnft.star.domain.rank.core.rank.model.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starnft.star.common.annotations.Desensitized;
import com.starnft.star.common.enums.SensitiveTypeEnum;
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
    @Desensitized(type = SensitiveTypeEnum.MOBILE_PHONE )
    private String mobile ;
    @ApiModelProperty(value = "账户")
    private String account;
    @ApiModelProperty(value = "是否有效  0(未实名) 1（已经实名）2（有效邀请")
    private Integer valid;
    @ApiModelProperty(value = "邀请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date invitationTime;
}
