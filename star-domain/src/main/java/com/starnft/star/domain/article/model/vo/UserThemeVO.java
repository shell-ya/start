package com.starnft.star.domain.article.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class UserThemeVO implements Serializable {
    @ApiModelProperty("主题id")
    private Long themeId;
    @ApiModelProperty("主题名称")
    private String themeName;
    @ApiModelProperty("主题图片")
    private String themeImages;
    @ApiModelProperty("主题藏品数量")
    private Integer nums;
    @ApiModelProperty("发行商名称")
    private String publisherName;
    @ApiModelProperty("发行商图标")
    private String publisherPic;
}
