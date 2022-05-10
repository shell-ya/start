package com.starnft.star.domain.theme.model.req;

import com.starnft.star.common.page.RequestPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
//@NoArgsConstructor
public class ThemeReq  extends RequestPage {
    @ApiModelProperty("是否推荐")
    private Boolean isRecommend;
    @ApiModelProperty("系列id")
    private Long seriesId;
}
