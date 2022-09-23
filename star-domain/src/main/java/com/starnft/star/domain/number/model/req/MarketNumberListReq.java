package com.starnft.star.domain.number.model.req;

import com.starnft.star.common.page.RequestPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketNumberListReq extends RequestPage implements Serializable {

    @ApiModelProperty("主题id")
    private Long themeId;

    @ApiModelProperty("排序类型 1：按编号排序 2：按价格")
    private Integer sortType;

    @ApiModelProperty("排序类型 1：升序 2：降序")
    private Integer sortOrder;

}
