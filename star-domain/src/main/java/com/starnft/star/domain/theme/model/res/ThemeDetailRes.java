package com.starnft.star.domain.theme.model.res;

import com.starnft.star.domain.publisher.model.vo.PublisherVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ThemeDetailRes {

    @ApiModelProperty(value = "主题id")
    private Long id;
    @ApiModelProperty(value = "系列id")
    private Long seriesId;
    @ApiModelProperty(value = "主题类别 1-藏品 2-盲盒")
    private Integer themeType;
    @ApiModelProperty(value = "主题名称")
    private String themeName;
    @ApiModelProperty(value = "主题图片")
    private String themePic;
    @ApiModelProperty(value = "发行数量")
    private Integer publishNumber;
    @ApiModelProperty(value = "主题描述")
    private String descrption;
    @ApiModelProperty(value = "主题等级")
    private Byte themeLevel;
    @ApiModelProperty(value = "库存")
    private Integer stock;
    @ApiModelProperty(value = "发行价格")
    private BigDecimal lssuePrice;
    private String tags;
    @ApiModelProperty(value = "作者")
    private PublisherVO publisherVO;
    @ApiModelProperty(value = "合约地址")
    private String contractAddress;
}
