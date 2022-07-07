package com.starnft.star.domain.rank.core.rank.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Date 2022/7/7 7:34 PM
 * @Author ： shellya
 */
@Data
@ApiModel
public class InvitationHistory {

    @ApiModelProperty(value = "邀请记录")
    private List<InvitationHistoryItem> items;
}
