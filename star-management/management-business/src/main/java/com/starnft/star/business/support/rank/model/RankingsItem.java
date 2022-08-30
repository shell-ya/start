package com.starnft.star.business.support.rank.model;

import com.starnft.star.common.annotation.Excel;
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
    @Excel(name = "手机号")
    private String phone;
    @ApiModelProperty(value = "账号")
    @Excel(name = "账号")
    private Long account;
    @ApiModelProperty(value = "总人数")
    @Excel(name = "总人数")
    private Long total;
    @ApiModelProperty(value = "有效人数")
    @Excel(name = "有效人数")
    private Long valid;
//    @Excel(name = "金")
//    private Long aurum = 0L;
//    @Excel(name = "银")
//    private Long argentum = 0L;
//    @Excel(name = "铜")
//    private Long cuprum = 0L;
}
