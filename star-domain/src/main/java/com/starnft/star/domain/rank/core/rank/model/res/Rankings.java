package com.starnft.star.domain.rank.core.rank.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Date 2022/7/6 11:23 PM
 * @Author ： shellya
 */
@Data
@ApiModel("排行榜")
public class Rankings {

    @ApiModelProperty(value = "排行榜名称")
    private String rankName;
    @ApiModelProperty(value = "数据")
    private List<RankingsItem> rankItem;
}
