package com.starnft.star.domain.rank.core.rank.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Date 2022/7/6 11:24 PM
 * @Author ： shellya
 */
@Data
@ApiModel
public class RankingsItem {
//    @ApiModelProperty(value = "排名")
//    private Integer rank;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "账号")
    private Long account;
    @ApiModelProperty(value = "总人数")
    private Integer total;
    @ApiModelProperty(value = "有效人数")
    private Integer valid;
}
