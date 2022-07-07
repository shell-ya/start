package com.starnft.star.domain.banner.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Date 2022/5/9 1:55 PM
 * @Author ： shellya
 */
@Data
@Builder
public class BannerVo {

    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("主图")
    private String imgUrl;
    @ApiModelProperty("展示位置")
    private Integer position;
    @ApiModelProperty("链接地址")
    private String url;
    @ApiModelProperty(" 跳转类型 0 不跳转 1 外链接 2 内链接")
    private Integer jumpType;
}
