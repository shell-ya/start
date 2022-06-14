package com.starnft.star.domain.number.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Harlan
 * @date 2022/05/23 12:41
 */
@Data
@ApiModel("商品列表查询参数")
public class NumberQueryRequest {
    @ApiModelProperty("主题类型 1：藏品  2：盲盒")
    private Integer themeType;
    @ApiModelProperty("排序字段 1：价格排序  2：编号排序  3：发行时间排序")
    private Integer orderBy;
    @ApiModelProperty("排序方式 1：升序  2：降序")
    private Integer sortType;
    @ApiModelProperty("系列id")
    private Long seriesId;
    @ApiModelProperty("主题id")
    private Long themeId;
    @ApiModelProperty("主题名称")
    private String themeName;
}
