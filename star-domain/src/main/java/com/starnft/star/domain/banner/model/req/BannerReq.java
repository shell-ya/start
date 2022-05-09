package com.starnft.star.domain.banner.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Date 2022/5/9 1:59 PM
 * @Author ： shellya
 */
@Data
@Builder
public class BannerReq {

    @ApiModelProperty("展示位置")
    private String type;
    @ApiModelProperty("轮播图数量")
    private int size;
}
