package com.starnft.star.application.process.user.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Date 2022/7/10 1:54 PM
 * @Author ： shellya
 */
@Data
@Builder
@ApiModel("邀请码返回数据")
public class ShardCodeRes {
    @ApiModelProperty(value = "邀请码")
    private String shardCode;
    @ApiModelProperty(value = "活动标记")
    private String activityType;
    @ApiModelProperty(value = "接口地址")
    private String url;

}
