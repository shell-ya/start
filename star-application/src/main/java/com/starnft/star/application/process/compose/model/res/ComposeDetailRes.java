package com.starnft.star.application.process.compose.model.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("合成详情")
public class ComposeDetailRes implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)

    @ApiModelProperty("合成ID")
    private Long id;
    @ApiModelProperty("合成名称")
    private String composeName;

    @ApiModelProperty("合成封面")
    private String composeImages;
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startedAt;
    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endAt;

    @ApiModelProperty("状态 0未开启 1进行中 2 已结束")
    private Integer composeStatus;

    @ApiModelProperty("合成说明 富文本")
    private String composeRemark;
    @ApiModelProperty("合成素材列表")
    private List<ComposeCategoryMaterialRes> composeCategoryMaterialResList;
}
