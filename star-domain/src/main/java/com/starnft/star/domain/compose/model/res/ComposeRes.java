package com.starnft.star.domain.compose.model.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class ComposeRes implements Serializable {
@ApiModelProperty("合成id")
    private Long id;
    @ApiModelProperty("合成标题")
    private String composeName;

    @ApiModelProperty("合成图片")
    private String composeImages;
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startedAt;
    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endAt;

    @ApiModelProperty("状态 0 未开始  1已开始  2 已结束")
    private Integer composeStatus;

    @ApiModelProperty("说明")
    private String composeRemark;

}
