package com.starnft.star.domain.article.model.req;

import com.starnft.star.common.page.RequestPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@ApiModel("用户主题查询参数")
public class UserHaveThemeReq extends RequestPage {

    @ApiModelProperty("系列id")
    private Long seriesId;

    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty("类型 1：藏品 2：盲盒")
    private String themeType;

    @ApiModelProperty("状态 0：已购买 1：挂售中 2：已出售")
    private String themeStatus;
}
