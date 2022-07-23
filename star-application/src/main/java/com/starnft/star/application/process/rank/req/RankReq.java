package com.starnft.star.application.process.rank.req;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Date 2022/7/10 3:07 PM
 * @Author ： shellya
 */
@Data
@ApiModel(value = "排行榜请求类")
public class RankReq {

//    @ApiModelProperty("排行榜名称")
//    private String rankName;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "是否有效 0(未实名) 1（已经实名）2（有效邀请） 不传返回全部")
    private Integer isValid;
}
