package com.starnft.star.application.process.props.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropInvokeReq implements Serializable {
    /**
     * uid
     */
    private Long userId;
    /**
     * 道具id
     */
    @ApiModelProperty(name = "道具id", required = true)
    @NotNull(message = "道具id 不能为空")
    private String propId;
    /**
     * 道具类型
     */
    @ApiModelProperty(name = "道具类型", required = true)
    @NotNull(message = "道具类型 不能为空")
    private Integer propType;
    /**
     * 道具等级
     */
    @ApiModelProperty(name = "道具等级", required = true)
    @NotNull(message = "道具等级 不能为空")
    private Integer propLevel;

}
