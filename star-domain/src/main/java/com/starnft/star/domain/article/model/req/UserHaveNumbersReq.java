package com.starnft.star.domain.article.model.req;

import com.starnft.star.common.page.RequestPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户藏品查询参数")
public class UserHaveNumbersReq extends RequestPage {

    @ApiModelProperty("主题id")
    private Long themeId;

    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty("类型 1：藏品 2：盲盒")
    private Integer themeType;

    @ApiModelProperty("状态 0：已购买 1：挂售中 2：已出售")
    private Integer status;
}
