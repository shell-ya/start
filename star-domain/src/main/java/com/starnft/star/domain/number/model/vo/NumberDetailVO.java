package com.starnft.star.domain.number.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Harlan
 * @date 2022/05/24 17:34
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApiModel
public class NumberDetailVO implements Serializable {
    @ApiModelProperty("商品id")
    private Long id;
    @ApiModelProperty("商品编号")
    private Long number;
    @ApiModelProperty("发行数量")
    private Integer issuedQty;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("商品图片")
    private String picture;
    @ApiModelProperty("合约地址")
    private String contractAddress;
    @ApiModelProperty("链上标识")
    private String chainIdentification;
    @ApiModelProperty("商品描述")
    private String descrption;
    @ApiModelProperty("商品价格")
    private BigDecimal price;
    @ApiModelProperty("商品状态 状态(0-未出售  1-已出售  2-挂售中)")
    private Integer status;
    @ApiModelProperty("商品类型(1-藏品 2-盲盒")
    private Integer type;
    @ApiModelProperty("拥有者")
    private String ownerBy;
}
