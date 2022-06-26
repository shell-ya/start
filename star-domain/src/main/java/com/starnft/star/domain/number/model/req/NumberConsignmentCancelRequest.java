package com.starnft.star.domain.number.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Harlan
 * @date 2022/06/26 20:41
 */
@Data
@ApiModel("商品取消寄售请求")
public class NumberConsignmentCancelRequest implements Serializable {
    @ApiModelProperty(value = "商品ID", required = true)
    @NotNull(message = "商品ID不能为空")
    private Long numberId;
}
