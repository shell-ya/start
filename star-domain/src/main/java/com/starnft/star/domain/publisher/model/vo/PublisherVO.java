package com.starnft.star.domain.publisher.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PublisherVO implements Serializable {
    @ApiModelProperty(value = "作者id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;
    @ApiModelProperty(value = "作者头像")
    private String authorPic;
    @ApiModelProperty(value = "作者名称")
    private String authorName;
    @ApiModelProperty(value = "发行商名称")
    private String publisherName;
    @ApiModelProperty(value = "发行商图片")
    private String publisherPic;
    @ApiModelProperty(value = "发行商id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long  publisherId;
}
