package com.starnft.star.domain.number.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Harlan
 * @date 2022/05/25 19:55
 */
@Data
@ApiModel("商品寄售请求")
public class NumberConsignmentRequest implements Serializable {

    @ApiModelProperty(value = "商品ID", required = true)
    @NotNull(message = "商品ID不能为空")
    private Long numberId;

    @ApiModelProperty(value = "寄售价格", required = true)
    @NotNull(message = "寄售价格不能为空")
    private BigDecimal price;

}
