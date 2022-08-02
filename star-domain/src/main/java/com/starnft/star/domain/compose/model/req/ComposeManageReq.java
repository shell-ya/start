package com.starnft.star.domain.compose.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("合成模型")
public class ComposeManageReq implements Serializable {
    @ApiModelProperty("合成素材ids")
    List<Long> sourceIds;
    @ApiModelProperty("合成类目id")
    Long categoryId;
    @ApiModelProperty("合成id")
    Long composeId;
    @ApiModelProperty(hidden = true)
    Long userId;
}
