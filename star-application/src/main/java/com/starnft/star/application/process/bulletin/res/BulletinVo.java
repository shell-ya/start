package com.starnft.star.application.process.bulletin.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Date 2022/7/24 4:49 PM
 * @Author ： shellya
 */
@Data
@ApiModel("公告列表")
public class BulletinVo {

    @ApiModelProperty("公告id")
    private Long id;
    @ApiModelProperty("公告详情")
    private String title;
}
