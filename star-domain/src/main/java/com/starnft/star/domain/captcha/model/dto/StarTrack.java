package com.starnft.star.domain.captcha.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Harlan
 * @date 2022/07/02 19:59
 */
@Data
public class StarTrack {
    @ApiModelProperty(value = "event.pageX")
    private Integer x;
    @ApiModelProperty(value = "event.pageY")
    private Integer y;
    @ApiModelProperty(value = "时间")
    private Integer t;
    @ApiModelProperty(value = "类型 move/up")
    private String type;
}
