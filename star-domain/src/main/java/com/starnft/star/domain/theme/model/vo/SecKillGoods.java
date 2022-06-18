package com.starnft.star.domain.theme.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "发行商品")
public class SecKillGoods implements Serializable {

    @ApiModelProperty(name = "库存", notes = "")
    private Integer stock;

    @ApiModelProperty(name = "发行数量", notes = "")
    private Integer goodsNum;

    @ApiModelProperty(name = "秒杀定价", notes = "")
    private BigDecimal secCost;

    @ApiModelProperty(name = "开始时间", notes = "")
    private Date startTime;

    @ApiModelProperty(name = "结束时间", notes = "")
    private Date endTime;

    @ApiModelProperty(name = "主题id", notes = "")
    private Long themeId;

    @ApiModelProperty(name = "系列id", notes = "")
    private Long  seriesId;

    @ApiModelProperty(name = "系列名称", notes = "")
    private String  seriesName;

    @ApiModelProperty(name = "主题类型", notes = "")
    private Integer themeType;

    @ApiModelProperty(name = "主题名称", notes = "")
    private String themeName;

    @ApiModelProperty(name = "主题图片", notes = "")
    private String themePic;

    @ApiModelProperty(name = "商品描述", notes = "")
    private String descrption;

    @ApiModelProperty(name = "主题级别", notes = "")
    private Byte themeLevel;
}
