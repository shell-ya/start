package com.starnft.star.domain.theme.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeGoodsVO implements Serializable {

    /**
     * 主键id
     */

    @ApiModelProperty(value = "主题id")
    private Long id;

    /**
     * 系列id
     */
    @ApiModelProperty(value = "系列id")
    private Long seriesId;

    /**
     * 发行商ID
     */
    @ApiModelProperty(value = "发行商ID")
    private Long publisherId;

    /**
     * 发行商名称
     */
    @ApiModelProperty(value = "发行商名称")
    private String publisherName;

    /**
     * 主题类型(1-藏品 2-盲盒)
     */
    @ApiModelProperty(value = "主题类型(1-藏品 2-盲盒)")
    private Integer themeType;

    /**
     * 主题名称
     */
    @ApiModelProperty(value = "主题名称")
    private String themeName;

    /**
     * 主题图片
     */
    @ApiModelProperty(value = "主题图片")
    private String themePic;

    /**
     * 发行数量
     */
    @ApiModelProperty(value = "发行数量")
    private Integer publishNumber;

    /**
     * 流通数量
     */
    @ApiModelProperty(value = "流通数量")
    private Integer circulate;

    /**
     * 主题级别
     */
    @ApiModelProperty(value = "主题级别")
    private Byte themeLevel;

    /**
     * 市场开放时间
     */
    @ApiModelProperty(value = "市场开放时间")
    private Date marketOpenTime;

    /**
     * 市场最低价
     */
    @ApiModelProperty(value = "市场最低价")
    private BigDecimal price;

}
