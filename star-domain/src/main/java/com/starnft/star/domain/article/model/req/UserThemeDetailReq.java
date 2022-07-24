package com.starnft.star.domain.article.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Date 2022/7/24 2:13 PM
 * @Author ： shellya
 */
@Data
@ApiModel("藏品详情查询参数")
public class UserThemeDetailReq {
    @ApiModelProperty("系列id")
    private Long numberId;
    @ApiModelProperty(hidden = true)
    private Long userId;
}
