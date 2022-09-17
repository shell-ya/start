package com.starnft.star.domain.theme.model.req;

import com.starnft.star.common.page.RequestPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ThemeGoodsReq extends RequestPage implements Serializable {

    @ApiModelProperty("系列id")
    private Long seriesId;

    @ApiModelProperty("排序类型 1：按发行时间排序 2：按价格")
    private Integer sortType;

    @ApiModelProperty("排序类型 1：升序 2：降序")
    private Integer sortOrder;

}
