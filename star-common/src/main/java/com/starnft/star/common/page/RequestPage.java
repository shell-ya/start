package com.starnft.star.common.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分页")
public class RequestPage implements Serializable {
    @ApiModelProperty("页数")
    private int page ;
    @ApiModelProperty("个数")
    private  int size;
}
