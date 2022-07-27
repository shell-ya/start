package com.starnft.star.domain.compose.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class ComposeManageReq implements Serializable {
    List<Long> sourceIds;
    Long categoryId;
    Long composeId;
    @ApiModelProperty(hidden = true)
    Long userId;
}
