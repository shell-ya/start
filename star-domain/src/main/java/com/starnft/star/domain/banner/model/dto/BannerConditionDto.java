package com.starnft.star.domain.banner.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Date 2022/5/9 9:13 PM
 * @Author ： shellya
 */
@Data
public class BannerConditionDto {

    @ApiModelProperty("排序")
    private int sort;
    @ApiModelProperty("展示位置：头部，TOP; 中间，MIDDLE；底部，BOTTOM;")
    private Integer position;
    @ApiModelProperty("是否展示：0，否；1，是")
    private Integer display;
    @ApiModelProperty("标题")
    private String title;

}
