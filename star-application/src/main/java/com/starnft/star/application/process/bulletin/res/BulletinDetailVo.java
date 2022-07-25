package com.starnft.star.application.process.bulletin.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Date 2022/7/24 4:52 PM
 * @Author ： shellya
 */
@Data
@ApiModel("公告详情数据")
public class BulletinDetailVo {

    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("发布时间")
    private Date publishTime;
    @ApiModelProperty("公告图")
    private String picurl;
    @ApiModelProperty("公告内容")
    private String content;

}
