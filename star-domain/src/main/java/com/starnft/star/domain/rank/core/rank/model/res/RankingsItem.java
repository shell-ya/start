package com.starnft.star.domain.rank.core.rank.model.res;

import com.starnft.star.common.annotations.Desensitized;
import com.starnft.star.common.enums.SensitiveTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Date 2022/7/6 11:24 PM
 * @Author ： shellya
 */
@Data
@ApiModel
public class RankingsItem implements Serializable {
//    @ApiModelProperty(value = "排名")
//    private Integer rank;
    @ApiModelProperty(value = "手机号")
    @Desensitized(type = SensitiveTypeEnum.MOBILE_PHONE)
    private String phone;
    @ApiModelProperty(value = "账号")
    private Long account;
    @ApiModelProperty(value = "总人数")
    private Long total;
    @ApiModelProperty(value = "有效人数")
    private Long valid;
}
